package cn.gzhu.test;

import cn.gzhu.test.customMybatis.MyDefaultSqlSession;
import cn.gzhu.test.customMybatis.MysqlSession;
import cn.gzhu.test.testExample.mapper.CityMapper;
import cn.gzhu.test.testExample.pojo.City;

/**
 * mybatis的参与角色与作用：
 * 1、mapper：拥有接口
 * 2、mapper.xml:拥有接口实现的sql
 * 3、executor:拥有连接池，封装了更新和查询的操作，只需要传递sql
 * 4、sqlSession:拥有executor、并且能为sqlSession进行动态代理，因为执行的sql是动态的，而execurot是不变的
 * 5、动态代理：组装mapper接口的所有方法，找到方法的对应的sql，拼装参数进sql，最后通过executor执行sql
 */
public class BootStrap {

    public static void main(String[] args) {

        StringBuffer sb = new StringBuffer();
        String a = "hello_hh_aa";
        String[] s = a.split("_");
        for (String q : s) {
            if (sb.length() < 1) {
                sb.append(q);
            } else {
                sb.append(q.substring(0, 1).toUpperCase());
                sb.append(q.substring(1).toLowerCase());
            }
        }
        System.out.println(sb.toString());
        //Start();
    }

    public static void Start() {
        MysqlSession session = new MyDefaultSqlSession();
        CityMapper cityMapper = session.getMapper(CityMapper.class);
        //代理业务类
        City city = cityMapper.selectById(1);
        System.out.println(city);
    }
}
