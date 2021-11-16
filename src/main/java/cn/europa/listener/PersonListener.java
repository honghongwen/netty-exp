package cn.europa.listener;

/**
 * @author fengwen
 * @date 2021-11-10
 *
 */
public interface PersonListener {

    /**
     * 跑路
     */
    void run(Event event);

    /**
     * 工作
     */
    void work(Event event);

    /**
     * 长大一岁
     */
    void grow(Event event);
}
