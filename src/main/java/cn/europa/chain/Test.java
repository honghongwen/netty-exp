package cn.europa.chain;

public class Test {

    @org.junit.Test
    public void testChain() {
        Chain chain = new Chain();
        chain.addChain(new Person1());
        chain.addChain(new Person2());
        chain.addChain(new Person3());

        Request request = new Request();
        request.setAge(18);
        chain.process(request);

    }
}
