package cn.gzhu.test.customMybatis;

import cn.gzhu.test.cutormJDKProxy.MyInvocationHandler;

import java.lang.reflect.Method;

/**
 * 描述：实现mapper接口
 */
public class MiniMapperHandler implements MyInvocationHandler {

    private MysqlSession mysqlSession;

    public MiniMapperHandler(MysqlSession mysqlSession) {
        this.mysqlSession = mysqlSession;
    }

    public Object invoke(Object proxy, Method method, Object[] args) {
        String mapperClass = method.getDeclaringClass().getName();
        //根据全类名找映射文件
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
