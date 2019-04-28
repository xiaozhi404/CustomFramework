package cn.gzhu.test.miniRPC.productApi;

import cn.gzhu.test.miniRPC.productService.api.Product;
import cn.gzhu.test.miniRPC.productService.api.ProductService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        ProductService productService = (ProductService) rpc(ProductService.class);
        Product product = productService.getById(1l);
        System.out.println(product);
    }

    /**
     * 通过动态代理进行远程调用
     * 调用哪个类的哪个方法，并传递给这个方法什么参数值
     * @param clazz
     * @return
     */
    public static Object rpc(Class clazz) {
        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 9999);
                String className = clazz.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeUTF(className);
                out.writeUTF(methodName);
                out.writeObject(parameterTypes);
                out.writeObject(args);
                out.flush();

                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Object o = in.readObject();
                out.close();
                in.close();
                socket.close();
                return o;
            }
        });

    }
}
