package cn.europa.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * netty初体验
 */
public class Server {

    ServerBootstrap bootstrap = new ServerBootstrap();

    public void startServer() {
        // 反应器线程组
        EventLoopGroup boos = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(4);
        try {

            bootstrap.group(boos, worker)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(9866))
                    // 设置通道参数
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    // 装配子通道流水线
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ServerHandler());
                        }
                    });

            ChannelFuture future = bootstrap.bind().sync();
            System.out.println("服务器启动成功，坚听端口:" + future.channel().localAddress());
            ChannelFuture closeFuture = future.channel().closeFuture();
            closeFuture.sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boos.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.startServer();
    }

}
