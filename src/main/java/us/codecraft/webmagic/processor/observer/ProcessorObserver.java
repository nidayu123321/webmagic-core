package us.codecraft.webmagic.processor.observer;

import us.codecraft.webmagic.Page;

/**
 * @author nidayu
 * @Description: 观察者接口！
 * @date 2016/3/17
 */
public interface ProcessorObserver {

    void afterRequest(Page page) throws Exception;

}
