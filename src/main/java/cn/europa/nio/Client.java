package cn.europa.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

    private void run() {
        try {
            SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 9890));
            socketChannel.configureBlocking(false);
            while (!socketChannel.finishConnect()) {

            }
            System.out.println("客户端连接成功");
            while (!Thread.interrupted()) {
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入内容>>");
                while (scanner.hasNext()) {
                    System.out.println("请输入内容>>");
                    String next = scanner.next();
                    if ("exit".equals(next)) {
                        socketChannel.close();
                    }
                    byteBuffer.put(next.getBytes());
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                    byteBuffer.clear();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}