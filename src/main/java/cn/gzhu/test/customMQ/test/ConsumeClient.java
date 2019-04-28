package cn.gzhu.test.customMQ.test;


import cn.gzhu.test.customMQ.Consumer;

/**
 * 描述：调用mq客户端,消费消息
 */
public class ConsumeClient {

    public static void main(String[] args) throws Exception {
        Consumer consumer = new Consumer();
        String message = consumer.consume();
        System.out.println("获取的消息为：" + message);
    }
}
