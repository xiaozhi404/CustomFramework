package cn.gzhu.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * 描述：执行器实现类
 */
public class MyBaseExecutor implements MyExecutor {

    private Connection con = null;
    private PreparedStatement pStatment = null;
    private ResultSet resultSet = null;


    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }catch (Exception e) {
            throw new RuntimeException("加载驱动失败");
        }
    }

    public <T> T query(String statement) {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/contest", "root", null);
            String sql = statement;
            pStatment = con.prepareStatement(sql);
            resultSet = pStatment.executeQuery();

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
