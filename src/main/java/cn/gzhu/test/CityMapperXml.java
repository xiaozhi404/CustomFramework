package cn.gzhu.test;

import java.util.HashMap;
import java.util.Map;

public class CityMapperXml {

    public static final String nameSpace = "cn.gzhu.test.CityMapper";

    private static Map<String, String> methodSqlMap = new HashMap<String, String>();

    static{
        methodSqlMap.put("selectById", "select * from tbl_city where id = ?");
    }

    public static String getMethodSql(String method) {
        return methodSqlMap.get(method);
    }
}
