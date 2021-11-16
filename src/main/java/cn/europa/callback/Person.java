package cn.europa.callback;

/**
 * 人
 */
public class Person implements Callback {

    private Machine machine;

    volatile boolean waterOpen = false;

    public Person(Machine machine) {
        this.machine = machine;
    }

    void eat() {
        machine.hotWater(this);
        System.out.println("吃饭");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("饭吃完了");
        while (true) {
            if (waterOpen) {
                System.out.println("洗碗");
                break;
            }
            System.out.println("等水开...玩手机");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void callBack() {
        System.out.println("水开了，可以开始洗碗");
        waterOpen = true;
    }
}
