package cn.europa.nio_multithread;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class IOHandler implements Runnable {

    private SocketChannel socketChannel;

    private SelectionKey key;

    static ExecutorService executorService = Executors.newFixedThreadPool(1);

    public IOHandler(Selector selector, SocketChannel socketChannel) throws IOException {
        this.socketChannel = socketChannel;
        socketChannel.configureBlocking(false);
        key = socketChannel.register(selector, SelectionKey.OP_READ);
        key.attach(this);
    }

    @Override
    public void run() {
        // 这里有点问题，为什么每次同个io事件会进来多次
//        System.out.println(Thread.currentThread().getName());
//        System.out.println(key);
//        System.out.println("isRead" + key.isReadable() + "isWrite" + key.isWritable() + "isAccept" + key.isAcceptable() + "isConnect" + key.isConnectable());
        executorService.execute(new Task());
    }


    class Task implements Runnable {

        @Override
        public void run() {
            IOHandler.this.handlerTask();
        }
    }

    private synchronized void handlerTask() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        int length = 0;
        try {
            StringBuffer builder = new StringBuffer();
            while ((length = socketChannel.read(byteBuffer)) > 0) {
                builder.append(new String(byteBuffer.array()).trim());
            }
            System.out.println(builder.toString());
            if (length == -1) {
                key.cancel();
                key.channel().close();
            }
        } catch (Exception e) {

        }
    }
}
