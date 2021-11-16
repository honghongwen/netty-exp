package cn.europa.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

    public static void main(String[] args) {
        Server server = new Server();
        server.run();
    }

    private void run() {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);

            ServerSocket serverSocket = serverChannel.socket();
            serverSocket.bind(new InetSocketAddress(9890));

            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务器启动成功");
            // 可以attach对象到key

            while (!Thread.interrupted()) {
                selector.select();
                Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                SelectionKey key = keys.next();

                if (key.isAcceptable()) {
                    // 连接上了
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();

                    // 注册新channel，并注册关注的IO事件。
                    SocketChannel socketChannel = server.accept();
                    socketChannel.configureBlocking(false);
                    SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                    selectionKey.attach("你好啊");
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    String attachment = (String) key.attachment();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    StringBuilder builder = new StringBuilder();
                    int length = 0;
                    while ((length = channel.read(buffer)) > 0) {
                        byte[] bytes = buffer.array();
                        builder.append(new String(bytes).trim());
                    }
                    String str = builder.toString();
                    if (!str.isEmpty()) {
                        System.out.println(attachment + str);
                    }
                    if (length == -1) {
                        key.cancel();
                        key.channel().close();
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
