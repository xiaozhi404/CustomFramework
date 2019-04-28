package cn.gzhu.test.customMQ.test;

import cn.gzhu.test.customMQ.Producer;

/**
 * 描述：调用mq客户端,生产消息
 */
public class ProduceClient {

    public static void main(String[] args) throws Exception {
        Producer producer = new Producer();
        producer.produce("SEND:Hello World");
    }

}
