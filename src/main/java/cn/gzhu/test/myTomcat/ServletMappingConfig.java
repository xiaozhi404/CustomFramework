package cn.gzhu.test.myTomcat;

import java.util.ArrayList;
import java.util.List;

/**
 * 描述：url和servlet类的映射
 * 作用：指定哪个URL交给哪个servlet进行处理
 */
public class ServletMappingConfig {

    public static List<ServletMapping> servletMappings = new ArrayList<ServletMapping>();

    static {
        servletMappings.add(new ServletMapping("/.*", "cn.gzhu.test.CustomSpringMVC.DispatcherServlet"));
        servletMappings.add(new ServletMapping("/hello", "cn.gzhu.test.myTomcat.HelloTestServlet"));
    }
}
