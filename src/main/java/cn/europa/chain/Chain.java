package cn.europa.chain;

import java.util.ArrayList;
import java.util.List;

public class Chain {

    private List<Handler> chains = new ArrayList<>();

    public void addChain(Handler handler) {
        chains.add(handler);
    }

    public void process(Request request) {
        for (Handler handler : chains) {
            Boolean flag = handler.process(request);
            if (flag != null) {
                System.out.println("request handle by " + handler.getClass().getSimpleName());
                return;
            }
        }
        System.out.println("nobody handler this request.");
    }
}
