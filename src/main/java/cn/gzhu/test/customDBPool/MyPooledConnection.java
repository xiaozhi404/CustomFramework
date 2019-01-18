package cn.gzhu.test.customDBPool;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * 描述：数据库连接管道，封装connection
 * 1.提供基本的sql查询功能
 * 2.提供逻辑关闭连接，做到连接复用
 */
@Data
@AllArgsConstructor
public class MyPooledConnection {

    private Connection connection;
    //逻辑关闭连接
    private Boolean isBusy;

    public void close() {
        isBusy = true;
    }

    public ResultSet query(String sql) {
        Statement statement;
        ResultSet resultSet = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }


}
