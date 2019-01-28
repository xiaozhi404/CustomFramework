package cn.gzhu.test.Utils;

public class MyStringUtils {

    public static String caseUnderlineToUp(String a) {
        StringBuffer sb = new StringBuffer();
        String[] s = a.split("_");
        for (String q : s) {
            if (sb.length() < 1) {
                sb.append(q);
            } else {
                sb.append(q.substring(0, 1).toUpperCase());
                sb.append(q.substring(1).toLowerCase());
            }
        }
        return sb.toString();
    }
}
