package cn.europa.callback;

/**
 * 烧水器
 */
public class Machine {

    public void hotWater(Callback callback) {
        new Thread(() -> {
            try {
                System.out.println("烧水中");
                Thread.sleep(5000);
                callback.callBack();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
