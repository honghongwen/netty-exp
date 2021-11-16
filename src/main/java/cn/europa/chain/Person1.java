package cn.europa.chain;

public class Person1 implements Handler {

    final static String FAVORITE = "APPLE";

    @Override
    public Boolean process(Request request) {
        if (request.getName() == null) {
            return null;
        }

        boolean isApple = FAVORITE.equals(request.getName());
        if(isApple) {
            System.out.println("Person1 LOVE APPLE!");
        } else {
            System.out.println("Person 1 DONT LIKE " + request.getName());
        }
        return isApple;
    }
}
