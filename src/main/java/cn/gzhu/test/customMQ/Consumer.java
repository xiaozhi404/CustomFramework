package cn.gzhu.test.customMQ;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class Consumer extends Thread  {

    //消费消息
    public String consume() throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            //先向消息队列发送命令
            out.println("CONSUME");
            out.flush();
            //再从消息队列获取一条消息
            String message = in.readLine();
            return message;
        }
    }

    @Override
    public void run() {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream())
        ) {
            //先向消息队列发送命令
            out.println("CONSUME");
            out.flush();
            //再从消息队列获取一条消息
            String message = in.readLine();

        } catch (Exception e) {

        }
    }
}
