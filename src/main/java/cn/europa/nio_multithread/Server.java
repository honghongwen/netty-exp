package cn.europa.nio_multithread;

public class Server {


    public static void main(String[] args) {
        Reactor reactor = new Reactor();
        Reactor.SubReactor[] subReactors = reactor.subReactors;
        new Thread(subReactors[0]).start();
    }
}
