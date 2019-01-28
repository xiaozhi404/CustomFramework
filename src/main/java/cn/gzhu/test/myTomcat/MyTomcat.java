package cn.gzhu.test.myTomcat;

import cn.gzhu.test.CustomSpringMVC.DispatcherServlet;
import cn.gzhu.test.customSpring.ApplicationContext;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 描述：模拟tomcat服务器
 * 作用：
 * 1.提供socket服务，优化点：BIO,NIO
 * 2.解析http协议，把请求和响应封装成request/response
 * 3.进行请求的分发,通过请求的uri找到servelet，通过反射的方式调用servlet类的service方法
 * ps:若要自定义uri对应servlet中的方法，
 * 1.可通过传递参数method，在service方法中判断进行调用
 * 2.spring mvc
 */
public class MyTomcat {

    private Integer port = 9090;
    //uri 和 servlet全名
    private Map<String, String> uriServletMap = new HashMap<String, String>();
    //servlet是单例的，每次生成后进行保存
    private Map<String, Object> servletContainer = new HashMap<String, Object>();
    //spring 容器
    private ApplicationContext applicationContext;
    //springmvc 前端控制器
    private DispatcherServlet dispatcherServlet;

    public MyTomcat(Integer port) {
        this.port = port;
        init();
    }

    public void init() {
        try {
            //1 加载servlet的映射
            for (ServletMapping servletMapping : ServletMappingConfig.servletMappings) {
                uriServletMap.put(servletMapping.getUrl(), servletMapping.getServletClass());
            }
            //2 初始化spring容器
            applicationContext = new ApplicationContext();
            applicationContext.init();
            //3 初始化spring mvc容器
            dispatcherServlet = new DispatcherServlet(applicationContext);
            dispatcherServlet.init();
            servletContainer.put(DispatcherServlet.class.getName(), dispatcherServlet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("my tomcat start in port:" + port);
            while (true) {
                System.out.println("端口监听中...");
                Socket socket = serverSocket.accept();
                InputStream inputStream = socket.getInputStream();
                OutputStream outputStream = socket.getOutputStream();
                MyRequest myRequest = new MyRequest(inputStream);
                MyResponse myResponse = new MyResponse(outputStream);
                //请求分发
                dispatch(myRequest, myResponse);
                socket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void dispatch(MyRequest myRequest, MyResponse myResponse) {
        //分发：通过反射调用servlet类的service方法
        try {
            //获取servlet全类名
            String servletClassName = null;
            Set<Map.Entry<String, String>> entries = uriServletMap.entrySet();
            for (Map.Entry<String, String> entry: entries) {
                if (null != myRequest.getUri() && myRequest.getUri().matches(entry.getKey())) {
                    servletClassName = entry.getValue();
                    break;
                }
            }
            if (null == servletClassName) {
                return;
            }
            //获取servlet实例
            MyServlet myServlet = (MyServlet) servletContainer.get(servletClassName);
            if (null == myServlet) {
                Class<MyServlet> clazz = (Class<MyServlet>) Class.forName(servletClassName);
                myServlet = clazz.newInstance();
                servletContainer.put(servletClassName, myServlet);
            }
            myServlet.service(myRequest, myResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        new MyTomcat(9092).start();
    }

}
