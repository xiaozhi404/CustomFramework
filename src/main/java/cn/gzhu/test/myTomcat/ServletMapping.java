package cn.gzhu.test.myTomcat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServletMapping {
    private String url;
    private String servletClass;
}
