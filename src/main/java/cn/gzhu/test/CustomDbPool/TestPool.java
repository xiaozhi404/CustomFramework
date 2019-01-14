package cn.gzhu.test.CustomDbPool;

import java.sql.ResultSet;

/**
 * 描述：连接池测试类
 */
public class TestPool {

    public static IMyPool iMyPool = MyPoolFactory.getInstance();

    public static void main(String[] args) throws Exception {
        for (int i = 0; i < 2; ++i) {
            MyPooledConnection myPooledConnection = iMyPool.getMyPooledConnection();
            ResultSet resultSet = myPooledConnection.query("select * from user");
            while (resultSet.next()) {
                System.out.print("id:"+resultSet.getString("id")+"   ");
                System.out.print("userName:"+resultSet.getString("user_name")+"   ");
                System.out.println("password:"+resultSet.getString("password"));

            }
        }


    }
}
