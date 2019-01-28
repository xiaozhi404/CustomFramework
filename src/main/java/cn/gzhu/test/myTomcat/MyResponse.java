package cn.gzhu.test.myTomcat;

import lombok.Data;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 描述：http 响应
 * 作用：通过Stream writer,基于http协议对客户端进行响应
 */
@Data
public class MyResponse {
    private OutputStream outputStream;

    public MyResponse(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    public void write(String content) throws IOException {
        StringBuffer httpResponse = new StringBuffer();
        httpResponse.append("HTTP/1.1 200 OK\n")
                .append("Content-Type: text/html\n")
                .append("\r\n")
                .append(content);
        outputStream.write(httpResponse.toString().getBytes());
        outputStream.close();
    }
}
