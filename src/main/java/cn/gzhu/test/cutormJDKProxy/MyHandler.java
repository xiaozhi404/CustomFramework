package cn.gzhu.test.cutormJDKProxy;

import java.lang.reflect.Method;

/**
 * 描述：业务处理类实现类
 * 作用：为代理类的所有方法添加事务代码
 */
public class MyHandler implements MyInvocationHandler {

    private UserService userService;

    public MyHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("******开启事务*****");
        Object result = method.invoke(userService, args[0]);
        System.out.println("******结束事务*****");
        return result;
    }
}
