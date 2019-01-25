package cn.gzhu.test.nioClientAndServerDemo;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioClientSocket {

    public static void main(String[] args) throws IOException {
        //打开 SocketChannel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 12345));
        //设置为 非阻塞
        socketChannel.configureBlocking(false);
        //给服务端发送信息
        socketChannel.write(ByteBuffer.wrap("hello world!".getBytes()));
        ByteBuffer readBuffer = ByteBuffer.allocate(512);
        while (true) {
            readBuffer.clear();
            int readBytes = socketChannel.read(readBuffer);
            if (readBytes > 0) {
                readBuffer.flip();
                System.out.println("Client: readBytes = " + readBytes);
                System.out.println("Client: data = " + new String(readBuffer.array(), 0, readBytes));
                socketChannel.close();
                break;
            }
        }
    }
}
