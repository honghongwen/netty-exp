package cn.europa.nio_multithread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 书本源码，重新写了一遍而已。
 * 主要加深对反应器模式｜结构化代码的理解。
 */
public class Reactor {

    // 两个selector,一个负责连接，一个负责处理其他io事件, 书本有bug,并不能做到这般。debug一下就会发现负责io事件的和accept的是同一个selector
    Selector[] selectors = new Selector[2];
    SubReactor[] subReactors;
    ServerSocketChannel serverChannel;
    AtomicInteger next = new AtomicInteger(0);

    public Reactor() {
        try {
            selectors[0] = Selector.open();
            selectors[1] = Selector.open();

            serverChannel = ServerSocketChannel.open();
            ServerSocket serverSocket = serverChannel.socket();
            serverSocket.bind(new InetSocketAddress(9890));
            serverChannel.configureBlocking(false);
            SelectionKey key = serverChannel.register(selectors[0], SelectionKey.OP_ACCEPT);
            key.attach(new AcceptHandler());

            System.out.println("服务器启动成功");

            SubReactor subReactor1 = new SubReactor(selectors[0]);
            SubReactor subReactor2 = new SubReactor(selectors[1]);
            subReactors = new SubReactor[]{subReactor1, subReactor2};
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class AcceptHandler implements Runnable {

        @Override
        public void run() {
            try {
                SocketChannel socketChannel = serverChannel.accept();
                if (socketChannel != null) {
                    new IOHandler(selectors[0], socketChannel);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (next.incrementAndGet() == selectors.length) {
                next.set(0);
            }
        }
    }

    class SubReactor implements Runnable {
        private Selector selector;

        public SubReactor(Selector selector) {
            this.selector = selector;
        }


        @Override
        public void run() {
            while (!Thread.interrupted()) {
                try {
                    selector.select();
                    Set<SelectionKey> keySet = selector.selectedKeys();
                    Iterator<SelectionKey> keys = keySet.iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        Runnable runnable = (Runnable) key.attachment();
                        if (runnable != null) {
                            if (key.isReadable() || key.isAcceptable())
                                runnable.run();
                        }
                    }
                    keySet.clear();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
