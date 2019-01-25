package cn.gzhu.test.nioClientAndServerDemo;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/** * SelectionKey 接口 实现类 * */
public class HandlerHandlerSelectionKeyImpl implements HandlerSelectionKey {

    @Override
    public void handler(SelectionKey key, Selector selector) throws IOException {
        int keyState = selectionKeyState(key);
        switch (keyState) {
            case SelectionKey.OP_ACCEPT:
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                accept(serverSocketChannel, selector);
                break;
            case SelectionKey.OP_READ:
                SocketChannel readSocketChannel = (SocketChannel) key.channel();
                read(readSocketChannel, selector);
                break;
            case SelectionKey.OP_WRITE:
                SocketChannel writeSocketChannel = (SocketChannel) key.channel();
                write(writeSocketChannel, selector);
                break;
        }
    }
    /** * 获取 SelectionKey 是什么事件 * @param key * @return */
    private int selectionKeyState(SelectionKey key) {
        if(key.isAcceptable()) {
            return SelectionKey.OP_ACCEPT;
        } else if(key.isReadable()) {
            return SelectionKey.OP_READ;
        } else if(key.isWritable()) {
            return SelectionKey.OP_WRITE;
        }
        return -1;
    }

    /** * 接口客户端请求 * @param serverSocketChannel * @param selector * @throws IOException */
    private void accept(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        //将 channel 注册到 Selector
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    /** * 读取客户端发送过来的信息 * @param socketChannel * @param selector * @throws IOException */
    private void read(SocketChannel socketChannel, Selector selector) throws IOException {
        ByteBuffer readBuffer = ByteBuffer.allocate(8192);
        int readBytes = socketChannel.read(readBuffer);
        if(readBytes > 0) {
            System.out.print("server receive: ");
            System.out.println(new String(readBuffer.array(), 0, readBytes));
        }
        //将 channel 注册到 Selector
        socketChannel.register(selector, SelectionKey.OP_WRITE);
    }

    /** * 响应客户端请求 * @param socketChannel * @param selector * @throws IOException */
    private void write(SocketChannel socketChannel, Selector selector) throws IOException {
        //响应消息
        String responseMsg = "hello client, i am server";
        byte[] responseByte = responseMsg.getBytes();
        ByteBuffer writeBuffer = ByteBuffer.allocate(responseByte.length);
        writeBuffer.put(responseByte);
        writeBuffer.flip();
        //响应客户端
        socketChannel.write(writeBuffer);
        socketChannel.finishConnect();
        socketChannel.close();
    }
}
