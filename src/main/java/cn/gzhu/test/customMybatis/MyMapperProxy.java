package cn.gzhu.test.customMybatis;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 描述：实现mapper接口
 */
public class MyMapperProxy implements InvocationHandler {

    private MysqlSession mysqlSession;


    public MyMapperProxy(MysqlSession mysqlSession) {
        this.mysqlSession = mysqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String mapperClass = method.getDeclaringClass().getName();
        if (CityMapperXml.nameSpace.equals(mapperClass)) {
            String methodName = method.getName();
            //根据方法名获取sql
            String sql = CityMapperXml.getMethodSql(methodName);
            //这里先把 %d 替换成参数
            String sqlWithArg = sql.replace("?", String.valueOf(args[0]));
            //执行sql
            return mysqlSession.selectById(sqlWithArg);
        }
        return null;
    }
}
