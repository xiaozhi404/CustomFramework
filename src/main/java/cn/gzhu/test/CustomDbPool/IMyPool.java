package cn.gzhu.test.CustomDbPool;

/**
 * 描述：对连接池的管理接口
 * 1.得到连接管道
 * 2.创建连接管道
 */
public interface IMyPool {

    MyPooledConnection getMyPooledConnection();

    void createMyPooledConnection(int count);
}
