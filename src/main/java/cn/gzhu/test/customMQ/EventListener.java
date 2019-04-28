package cn.gzhu.test.customMQ;

/**
 * 监听器接口
 */
public interface EventListener {
    //事件处理
    void handleEvent(String mesge);
}
