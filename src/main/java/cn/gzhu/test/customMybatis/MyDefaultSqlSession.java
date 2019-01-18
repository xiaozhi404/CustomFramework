package cn.gzhu.test.customMybatis;


import cn.gzhu.test.cutormJDKProxy.MyClassLoader;
import cn.gzhu.test.cutormJDKProxy.MyProxy;

public class MyDefaultSqlSession implements MysqlSession {

    private MyExecutor executor = new MyBaseExecutor();

    public <T> T selectById(String sql) {
        return executor.query(sql);
    }

    public <T> T getMapper(Class<T> clazz) {
        //return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyMapperProxy(this));
        return (T) MyProxy.newProxyInstace(new MyClassLoader("/Users/xiaozhi/Desktop/gitLocal/MiniMybatis/src/main/java/cn/gzhu/test/customMybatis", "cn.gzhu.test.customMybatis"),
                CityMapper.class,
                new MiniMapperHandler(this));
    }
}
