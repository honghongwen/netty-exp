package cn.europa.chain;

public interface Handler {

    /**
     * true 通过
     * false 不通过
     * null 不处理
     * @param request
     * @return
     */
    Boolean process(Request request);
}
