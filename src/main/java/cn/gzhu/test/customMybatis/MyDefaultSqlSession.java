package cn.gzhu.test.customMybatis;


import java.lang.reflect.Proxy;

public class MyDefaultSqlSession implements MysqlSession {

    private MyExecutor executor = new MyBaseExecutor();

    public <T> T selectById(String sql) {

        return executor.query(sql);
    }

    public <T> T getMapper(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyMapperProxy(this));
    }
}
