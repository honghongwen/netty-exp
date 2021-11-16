package cn.europa.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class Client {

    Bootstrap bootstrap = new Bootstrap();

    public void startClient() {
        EventLoopGroup worker = new NioEventLoopGroup(1);

        ChannelFuture future = bootstrap.group(worker)
                .channel(NioSocketChannel.class)
                .remoteAddress(new InetSocketAddress("127.0.0.1", 9866))
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 啥事不干
                    }
                })
                .connect();

        future.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("客户端连接成功");
                }
            }
        });

        try {
            future.sync();
            Channel channel = future.channel();

            System.out.println("请输入内容>>");
            Scanner scanner = new Scanner(System.in);
            String next = scanner.next();
            for (int i = 0; i < 1000; i++) {
                ByteBuf byteBuf = channel.alloc().buffer();
                byteBuf.writeBytes(next.getBytes("UTF-8"));
                channel.writeAndFlush(byteBuf);
            }

        } catch (InterruptedException | UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.startClient();
    }
}
