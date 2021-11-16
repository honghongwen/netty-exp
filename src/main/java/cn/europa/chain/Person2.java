package cn.europa.chain;

public class Person2 implements Handler {

    final static Integer LOVE = 18;

    @Override
    public Boolean process(Request request) {
        if (request.getAge() == null) {
            return null;
        }

        Integer age = request.getAge();
        if (age == 26) {
            System.out.println("Person2 Likes girls who is 18");
            return true;
        }

        System.out.println("Person2 dont like.");
        return false;
    }
}
