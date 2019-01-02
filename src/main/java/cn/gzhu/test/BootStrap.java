package cn.gzhu.test;

public class BootStrap {
    public static void main(String[] args) {
        Start();
    }


    public static void Start() {
        MysqlSession session = new MyDefaultSqlSession();
        CityMapper cityMapper = session.getMapper(CityMapper.class);
        City city = cityMapper.selectById(1);
        System.out.println(city);
    }
}
