package cn.gzhu.test.customMybatis;

/**
 * 1.负责mapper接口的实现
 * 2.负责执行操作数据库
 *   需要sql和参数
 */
public interface MysqlSession {

    <T> T selectById(String sql);

    /**
     * 实现mapper接口
     * 根据类的nameSpace和方法名获取sql
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getMapper(Class<T> clazz);
}
