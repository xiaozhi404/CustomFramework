package cn.gzhu.test.customMQ;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Producer {

    //生产消息
    public void produce(String message) throws Exception {
        //本地的的BrokerServer.SERVICE_PORT 创建SOCKET
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (PrintWriter out = new PrintWriter(socket.getOutputStream())) {
            out.println(message);
            out.flush();
        }
    }

}
