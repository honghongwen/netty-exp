package cn.europa.listener;

public class Event {

    private Person person;

    public Event(Person person) {
        this.person = person;
    }

    public Person getSource() {
        return person;
    }
}
