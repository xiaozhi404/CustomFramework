package cn.gzhu.test.customMybatis;

import cn.gzhu.test.cutormJDKProxy.MyInvocationHandler;
import cn.gzhu.test.testExample.mapperFile.CityMapperXml;

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
            //把 ？替换成参数值
            if (sql.contains("?")) {
                sql = sql.replace("?", String.valueOf(args[0]));
            }
            //如何判断session的执行时机？
            //包含select 为查询，否则为更新
            //在根据返回值类型执行对应的方法
            //执行sql
            return mysqlSession.selectAll(sql, CityMapperXml.className);
        }
        return null;
    }
}
