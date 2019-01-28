package cn.gzhu.test.testExample.mapperFile;

import java.util.HashMap;
import java.util.Map;

public class CityMapperXml {
    public static String className = "cn.gzhu.test.testExample.pojo.City";

    public static final String nameSpace = "cn.gzhu.test.testExample.mapper.CityMapper";

    private static Map<String, String> methodSqlMap = new HashMap<String, String>();

    static{
        methodSqlMap.put("selectById", "select * from tbl_city where id = ?");
        methodSqlMap.put("getCitys", "select * from tbl_city");
    }

    //根据方法名获取sql
    public static String getMethodSql(String method) {
        return methodSqlMap.get(method);
    }
}
