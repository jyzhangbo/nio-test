package com.github.buffer;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author:zhangbo
 * @Date:2019/1/10 15:14
 */
public class BufferTest {

    public static void main(String[] args) throws Exception{
        RandomAccessFile accessFile = new RandomAccessFile("zhangbo.text","rw");
        FileChannel channel = accessFile.getChannel();

        //buffer的分配
        ByteBuffer buf = ByteBuffer.allocate(4);

        //向buffer中写入数据
        int byteRead = channel.read(buf);

        while (byteRead != -1){
            System.out.println("Read "+byteRead);

            //将buffer从写模式切换到读模式
            buf.flip();
            while (buf.hasRemaining()){
                System.out.println((char)buf.get());
            }

            buf.clear();
            byteRead = channel.read(buf);
        }

        accessFile.close();
    }

}
