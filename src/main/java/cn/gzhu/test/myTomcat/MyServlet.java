package cn.gzhu.test.myTomcat;

/**
 * 描述：tomcat是基于servlet规范的
 * 作用：提供api接口, 若要调用其它自定义的方法，可以通过传递参数method进行通知
 */
public abstract class MyServlet {

    public abstract void doGet(MyRequest myRequest, MyResponse myResponse) throws Exception;

    public abstract void doPost(MyRequest myRequest, MyResponse myResponse) throws Exception;

    public void service(MyRequest myRequest, MyResponse myResponse) throws Exception {
        if ("POST".equalsIgnoreCase(myRequest.getMethod())) {
            doPost(myRequest, myResponse);
        } else if ("GET".equalsIgnoreCase(myRequest.getMethod())) {
            doGet(myRequest, myResponse);
        }
    }
}
