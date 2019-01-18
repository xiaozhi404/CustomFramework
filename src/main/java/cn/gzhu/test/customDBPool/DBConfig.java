package cn.gzhu.test.customDBPool;

/**
 * 描述：数据库配置信息
 */
public class DBConfig {
    //连接数据库
    public static final String jdbcDriver = "com.mysql.cj.jdbc.Driver";
    //测试连接池的url
    //public static final String jdbcUrl = "jdbc:mysql://localhost:3306/test";
    public static final String jdbcUrl = "jdbc:mysql://localhost:3306/contest?useSSL=false";
    public static final String userName = "root";
    public static final String password = "";
    //连接池的配置
    public static final Integer initCount = 10;
    //连接池不足的时候,增长量
    public static final Integer step = 2;
    //连接池的最大数量
    public static final Integer maxCount = 50;
}
