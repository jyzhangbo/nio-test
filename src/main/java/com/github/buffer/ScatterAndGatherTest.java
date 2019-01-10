package com.github.buffer;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author:zhangbo
 * @Date:2019/1/10 15:46
 */
public class ScatterAndGatherTest {

    public static void main(String[] args) throws Exception{
        RandomAccessFile accessFile = new RandomAccessFile("zhangbo.text","rw");
        FileChannel channel = accessFile.getChannel();

        //buffer的分配
        ByteBuffer buf1 = ByteBuffer.allocate(4);
        ByteBuffer buf2 = ByteBuffer.allocate(4);

        ByteBuffer[] buf = {buf1,buf2};

        //一个channel到多个buffer
        Long byteRead = channel.read(buf);

        RandomAccessFile writeFile = new RandomAccessFile("zhangbo1.text","rw");
        FileChannel channel2 = writeFile.getChannel();

        while (byteRead != -1){
            System.out.println("Read "+byteRead);

            //将buffer从写模式切换到读模式
            buf1.flip();
            buf2.flip();

            //多个buffer到一个channel
            channel2.write(buf);

            buf1.clear();
            buf2.clear();

            byteRead = channel.read(buf);
        }

        accessFile.close();
        writeFile.close();
    }

}
