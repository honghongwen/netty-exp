package cn.europa.listener;

import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    public static void main(String[] args) {
        Person person = new Person();

        person.registerListener(new PersonListener() {
            @Override
            public void run(Event event) {
                System.out.println("打死他");
            }

            @Override
            public void work(Event event) {
            }

            @Override
            public void grow(Event event) {
                AtomicInteger age = event.getSource().age;
                System.out.println("又大了一岁，现在" + age.get() + "了");
            }
        });

        person.run();
        person.grow();
    }
}
