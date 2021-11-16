package cn.europa.listener;

import java.util.concurrent.atomic.AtomicInteger;

public class Person {

    PersonListener listener;

    public AtomicInteger age = new AtomicInteger(0);

    public void run() {
        System.out.println("跑路");
        listener.run(new Event(this));
    }

    public void grow() {
        age.getAndIncrement();
        listener.grow(new Event(this));
    }

    public void registerListener(PersonListener listener) {
        this.listener = listener;
    }
}

