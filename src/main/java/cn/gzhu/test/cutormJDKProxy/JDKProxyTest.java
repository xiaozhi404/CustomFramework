package cn.gzhu.test.cutormJDKProxy;

public class JDKProxyTest {

    public static void main(String[] args) {
        UserServiceImpl userService = new UserServiceImpl();
        MyHandler myHandler = new MyHandler(userService);
        UserService proxyInstace = (UserService) MyProxy.newProxyInstace(
                new MyClassLoader("/Users/xiaozhi/Desktop/gitLocal/MiniMybatis/src/main/java/cn/gzhu/test/cutormJDKProxy", "cn.gzhu.test.cutormJDKProxy"),
                UserService.class,
                myHandler);
        System.out.println(proxyInstace.getClass().getName());
        proxyInstace.findUser(2);

    }
}
