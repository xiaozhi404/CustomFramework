package cn.gzhu.test.myTomcat;

import lombok.Data;

import java.io.IOException;
import java.io.InputStream;

/**
 * 描述：http请求
 * 作用：通过输入流，对HTTP协议进行解析，拿到了HTTP请求头的方法以及URL
 */
@Data
public class MyRequest {

    private String uri;

    private String method;

    public MyRequest(InputStream inputStream) throws IOException {

        byte[] httpRequestBytes = new byte[1024];
        String httpRequest = "";
        int len = 0;
        if ((len = inputStream.read(httpRequestBytes)) > 0) {
            httpRequest = new String(httpRequestBytes, 0, len);
        }
        if (!"".equals(httpRequest)) {
            String requestRow = httpRequest.split("\n")[0];
            uri = requestRow.split("\\s")[1];
            method = requestRow.split("\\s")[0];
        }
    }

}
