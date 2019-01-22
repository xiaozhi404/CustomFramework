package cn.gzhu.test.myTomcat;

public class HelloTestServlet extends MyServlet {
    @Override
    public void doGet(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("hello, Get");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doPost(MyRequest myRequest, MyResponse myResponse) {
        try {
            myResponse.write("hello, Post");
        } catch (Exception e) {

        }
    }
}
