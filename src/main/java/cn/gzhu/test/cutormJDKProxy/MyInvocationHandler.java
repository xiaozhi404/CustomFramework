package cn.gzhu.test.cutormJDKProxy;


import java.lang.reflect.Method;

/**
 * 业务处理类
 */
public interface MyInvocationHandler {

    Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
