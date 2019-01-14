package cn.gzhu.test;

import cn.gzhu.test.customMybatis.CityMapper;
import cn.gzhu.test.customMybatis.MyDefaultSqlSession;
import cn.gzhu.test.customMybatis.MysqlSession;
import cn.gzhu.test.pojo.City;

public class BootStrap {
    public static void main(String[] args) throws Exception {
        Start();
    }


    public static void Start() {
        MysqlSession session = new MyDefaultSqlSession();
        CityMapper cityMapper = session.getMapper(CityMapper.class);
        City city = cityMapper.selectById(1);
        System.out.println(city);
    }
}
