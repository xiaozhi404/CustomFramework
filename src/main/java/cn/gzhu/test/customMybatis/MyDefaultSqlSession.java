package cn.gzhu.test.customMybatis;


import cn.gzhu.test.cutormJDKProxy.MyClassLoader;
import cn.gzhu.test.cutormJDKProxy.MyProxy;

import java.util.List;

/**
 * mybatis的参与角色与作用：
 * 1、mapper：拥有接口
 * 2、mapper.xml:拥有接口实现的sql
 * 3、executor:拥有连接池，封装了更新和查询的操作，只需要传递sql
 * 4、sqlSession:拥有executor、并且能为sqlSession进行动态代理，因为执行的sql是动态的，而execurot是不变的
 * 5、动态代理：组装mapper接口的所有方法，找到方法的对应的sql，拼装参数进sql，最后通过executor执行sql
 */
public class MyDefaultSqlSession implements MysqlSession {

    private MyExecutor executor = new MyBaseExecutor();

    public <T> T selectById(String sql, String className) {
        List<T> list = executor.query(sql, className);
        return list.get(0);
    }

    @Override
    public <T> List<T> selectAll(String sql, String className) {
        List<T> list = executor.query(sql, className);
        return list;
    }

    public <T> T getMapper(Class<T> clazz) {
        //return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new MyMapperProxy(this));
        return (T) MyProxy.newProxyInstace(
                new MyClassLoader(clazz.getResource("").getPath(), clazz.getPackage().getName()),
                clazz,
                new MiniMapperHandler(this));
    }
}
