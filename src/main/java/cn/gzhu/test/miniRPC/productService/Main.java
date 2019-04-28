package cn.gzhu.test.miniRPC.productService;

import cn.gzhu.test.miniRPC.productService.api.ProductService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(9999);
        while (true) {
            //接收请求，意味着进行rpc远程调用
            Socket socket = serverSocket.accept();
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            //读取网络协议，即调用的类信息
            String className = in.readUTF();
            String methodName = in.readUTF();
            Class[] paramTyps = (Class[]) in.readObject();
            Object[] paramValues = (Object[]) in.readObject();

            Class clazz = null;
            //注册中心：维护api到具体实现到映射关系
            //寻找api的具体实现
            if (className.equals(ProductService.class.getName())) {
                clazz = ProductServiceImpl.class;
            }
            Method method = clazz.getMethod(methodName, paramTyps);
            Object result = method.invoke(clazz.newInstance(), paramValues);
            //返回调用结果
            out.writeObject(result);
            out.flush();

            in.close();
            out.close();
            socket.close();
        }
    }
}
