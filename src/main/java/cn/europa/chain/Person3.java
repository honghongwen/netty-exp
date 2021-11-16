package cn.europa.chain;

public class Person3 implements Handler {



    @Override
    public Boolean process(Request request) {
        if (request.getScore() == null) {
            return null;
        }

        if (request.getScore() > 80) {
            System.out.println("Person3 agree because he think this is a good student.");
            return true;
        }

        System.out.println("Person3 think its not good.");
        return false;
    }
}
