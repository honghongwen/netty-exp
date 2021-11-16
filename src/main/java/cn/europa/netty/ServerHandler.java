package cn.europa.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCounted;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        try {
            System.out.println("收到消息如下:");
            int able = in.readableBytes();
            byte[] bytes = new byte[able];
            in.readBytes(bytes);
            System.out.println(new String(bytes));
        } finally {
            if (msg instanceof ReferenceCounted) {
                ((ReferenceCounted) msg).release();
            }
        }
    }
}
