package cn.europa.callback;

public class Test {

    public static void main(String[] args) {
        Machine machine = new Machine();
        Person person = new Person(machine);

        person.eat();
    }
}
