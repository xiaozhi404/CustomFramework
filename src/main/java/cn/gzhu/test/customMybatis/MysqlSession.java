package cn.gzhu.test.customMybatis;

import java.util.List;

/**
 * 1.负责mapper接口的实现
 * 2.负责执行操作数据库  (sql里面已经带参数)
 */
public interface MysqlSession {

    <T> T selectById(String sql, String className);

    <T> List<T> selectAll(String sql, String className);
    /**
     * 实现mapper接口
     * 根据类的nameSpace和方法名获取sql
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> clazz);
}
