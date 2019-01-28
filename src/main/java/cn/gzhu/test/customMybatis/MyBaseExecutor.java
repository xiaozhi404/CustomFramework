package cn.gzhu.test.customMybatis;

import cn.gzhu.test.Utils.MyBeanUtils;
import cn.gzhu.test.Utils.MyStringUtils;
import cn.gzhu.test.customDBPool.IMyPool;
import cn.gzhu.test.customDBPool.MyPoolFactory;
import cn.gzhu.test.customDBPool.MyPooledConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 描述：执行器实现类
 */
public class MyBaseExecutor implements MyExecutor {

    private IMyPool pool = MyPoolFactory.getInstance();
    private PreparedStatement pStatment = null;
    private ResultSet resultSet = null;


    static {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e) {
            throw new RuntimeException("加载驱动失败");
        }
    }

    /**
     * 泛型方法的作用：例如在A方法中，返回City,强转成T,
     * 当调用A方法的时候，就可以用City直接接收，不必强转，若使用Object就要强转
     * @param sql
     * @param <T>
     * @return
     */
    public <T> T query(String sql, String className) {
        try {
            MyPooledConnection myPooledConnection = pool.getMyPooledConnection();
            ResultSet resultSet = myPooledConnection.query(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            Class<?> clazz = Class.forName(className);
            List result = new ArrayList<>();
            while (resultSet.next()) {
                Object o = clazz.newInstance();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String propName = MyStringUtils.caseUnderlineToUp(metaData.getColumnName(i + 1));
                    MyBeanUtils.setProperty(o, propName, resultSet.getObject(i + 1));
                }
//
//                City city = new City();
//                city.setId(resultSet.getInt("id"));
//                city.setCreatedAt(resultSet.getDate("created_at"));
//                city.setUpdatedAt(resultSet.getDate("updated_at"));
//                city.setName(resultSet.getString("name"));
//                city.setProvinceId(resultSet.getInt("province_id"));
                result.add(o);
            }
            myPooledConnection.close();
            return (T) result;
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


}
