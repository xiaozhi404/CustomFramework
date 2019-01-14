package cn.gzhu.test.customMybatis;

/**
 * 描述：定义操作数据库的动作
 */
public interface MyExecutor {

    /**
     * 查询操作
     * @param statement
     * @param <T>
     * @return
     */
    <T> T query(String statement);

}
