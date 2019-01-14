package com.github.selector;

import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Author:zhangbo
 * @Date:2019/1/10 17:54
 */
public class WebServer {

    public static void main(String[] args) throws Exception{
        //打开ServerSocketChannel
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress("127.0.0.1",8888));
        channel.configureBlocking(false);

        Selector selector = Selector.open();
        channel.register(selector, SelectionKey.OP_ACCEPT);

        ByteBuffer bb = ByteBuffer.allocate(16);

        RandomAccessFile writeFile = new RandomAccessFile("zhangbo4.text","rw");
        FileChannel channel2 = writeFile.getChannel();

        while (true){
            int readyNum = selector.select();
            if(readyNum == 0){
                continue;
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                SelectionKey selectionKey = iterator.next();
                if(selectionKey.isAcceptable()){
                    //接受连接
                    ServerSocketChannel ssChannel = (ServerSocketChannel) selectionKey.channel();
                    SocketChannel socketChannel = ssChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector,SelectionKey.OP_READ | SelectionKey.OP_WRITE);

                }else if (selectionKey.isReadable()) {
                    // 通道可读
                    SocketChannel sc = (SocketChannel) selectionKey.channel();
                    bb.clear();
                    while (sc.read(bb) > 0){
                        bb.flip();
                        channel2.write(bb);
                        bb.clear();
                    }
                } else if (selectionKey.isWritable()) {
                    // 通道可写
                    SocketChannel sChannel = (SocketChannel) selectionKey.channel();
                    bb.clear();
                    bb.put("zhangbo".getBytes());
                    while (bb.hasRemaining()) {
                        sChannel.write(bb);
                    }
                }

                iterator.remove();
            }
        }
    }
}
