package cn.gzhu.test.customMybatis;

import cn.gzhu.test.CustomDbPool.IMyPool;
import cn.gzhu.test.CustomDbPool.MyPoolFactory;
import cn.gzhu.test.CustomDbPool.MyPooledConnection;
import cn.gzhu.test.pojo.City;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 描述：执行器实现类
 */
public class MyBaseExecutor implements MyExecutor {

    private IMyPool pool = MyPoolFactory.getInstance();
    private PreparedStatement pStatment = null;
    private ResultSet resultSet = null;


    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e) {
            throw new RuntimeException("加载驱动失败");
        }
    }

    public <T> T query(String sql) {
        try {
            MyPooledConnection myPooledConnection = pool.getMyPooledConnection();
            ResultSet resultSet = myPooledConnection.query(sql);
            City city = new City();
            if (resultSet.next()) {
                city.setId(resultSet.getInt("id"));
                city.setCreatedAt(resultSet.getDate("created_at"));
                city.setName(resultSet.getString("name"));
                city.setProvinceId(resultSet.getInt("province_id"));
            }
            return (T)city;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
