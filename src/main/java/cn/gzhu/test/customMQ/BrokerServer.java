package cn.gzhu.test.customMQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * 描述：broker服务类
 * 作用：
 * 1.启动消息处理中心
 * 2.解析通讯协议，通过调用broker，处理socket请求
 */
public class BrokerServer implements Runnable {

    public static int SERVICE_PORT = 9999;
    private final Socket socket;
    private List<EventListener> listeners;

    public BrokerServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream())
        )
        {
            while (true) {
                String str = in.readLine();
                if (str == null) {
                    continue;
                }
                System.out.println("接收到原始数据：" + str);

                if (str.equals("CONSUME")) { //CONSUME 表示要消费一条消息
                    //从消息队列中消费一条消息
                    String message = Broker.consume();
                    out.println(message);
                    out.flush();
                } else if (str.contains("SEND:")){
                    //接受到的请求包含SEND:字符串 表示生产消息放到消息队列中
                    Broker.produce(str);
                    for (EventListener listener: listeners) {
                        listener.handleEvent(str.replace("SEND:", ""));
                    }
                }else {
                    System.out.println("原始数据:"+str+"没有遵循协议,不提供相关服务");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(SERVICE_PORT);
        System.out.println("服务器已经启动。。。。");
        while (true) {
            BrokerServer brokerServer = new BrokerServer(server.accept());
            new Thread(brokerServer).start();
        }
    }
}
