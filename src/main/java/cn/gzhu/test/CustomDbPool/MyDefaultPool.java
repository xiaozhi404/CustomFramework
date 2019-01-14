package cn.gzhu.test.CustomDbPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 描述：连接池实现类
 * 1.加载配置进行初始化
 * 2.加载数据库驱动
 * 3.防止多个线程从池中获取连接，需要考虑用什么存储
 */
public class MyDefaultPool implements IMyPool {

    private List<MyPooledConnection> pool = new CopyOnWriteArrayList<MyPooledConnection>();
    //连接数据库
    private String jdbcUrl;
    private String userName;
    private String password;
    //连接池的配置
    private Integer initCount;
    //连接池不足的时候,增长量
    private Integer step;
    //连接池的最大数量
    private Integer maxCount;

    public MyDefaultPool() {
        //初始化数据库连接池
        init();
        //加载驱动
        try {
            Class.forName(DBConfig.jdbcDriver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //初始化数据库连接池的管道
        createMyPooledConnection(initCount);
    }

    private void init() {
        jdbcUrl = DBConfig.jdbcUrl;
        userName = DBConfig.userName;
        password = DBConfig.password;
        initCount = DBConfig.initCount;
        step = DBConfig.step;
        maxCount = DBConfig.maxCount;
    }


    @Override
    public MyPooledConnection getMyPooledConnection() {
        if (pool.size() < 1) {
            throw new RuntimeException("连接池初始化失败");
        }
        MyPooledConnection realConnectionFromPool = null;
        try {
            realConnectionFromPool = getRealConnectionFromPool();
            //在高并发的情况下，可能一创建出来就全部消费了
            while (null == realConnectionFromPool) {
                createMyPooledConnection(step);
                realConnectionFromPool = getRealConnectionFromPool();
            }
            return realConnectionFromPool;
        } catch (Exception e) {

        }

        return null;
    }

    public synchronized MyPooledConnection getRealConnectionFromPool() throws Exception {
        for (MyPooledConnection myPooledConnection : pool) {
            //判断连接是否能用
            if (!myPooledConnection.getIsBusy()) {
                //判断连接在3s内是否有效
                if (myPooledConnection.getConnection().isValid(3000)) {
                    myPooledConnection.setIsBusy(true);
                    return myPooledConnection;
                } else {
                    //替换，使原来的connetion进行垃圾回收
                    Connection connection = createConnection();
                    myPooledConnection.setConnection(connection);
                    myPooledConnection.setIsBusy(true);
                    return myPooledConnection;
                }
            }
        }
        //若连接池的每个连接都很忙，则返回空
        return null;
    }

    @Override
    public void createMyPooledConnection(int count) {
        if (pool.size() + count > maxCount || maxCount < count) {
            throw new RuntimeException("超过初始化最大值");
        }

        for (int i = 0; i < count; ++i) {
            try {
                MyPooledConnection myPooledConnection = new MyPooledConnection(createConnection(), false);
                pool.add(myPooledConnection);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Connection createConnection() throws Exception {
        return DriverManager.getConnection(jdbcUrl, userName, password);
    }
}
