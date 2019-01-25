package cn.gzhu.test.nioClientAndServerDemo;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;

public interface HandlerSelectionKey {
    void handler(SelectionKey key, Selector selector) throws IOException;
}
