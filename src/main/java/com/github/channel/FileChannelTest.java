package com.github.channel;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author:zhangbo
 * @Date:2019/1/10 15:02
 */
public class FileChannelTest {

    public static void main(String[] args) throws Exception{

        //打开FileChannel
        RandomAccessFile file =new RandomAccessFile("zhangbo.text","rw");
        FileChannel channel = file.getChannel();

        //从FileChannel中读取数据到buffer中
        ByteBuffer byteBuffer = ByteBuffer.allocate(48);
        int size = channel.read(byteBuffer);
        System.out.println(size);

        channel.position(channel.position() + 100);
        String newData = "new string"+System.currentTimeMillis();
        ByteBuffer buffer = ByteBuffer.allocate(newData.length());
        buffer.put(newData.getBytes());
        buffer.flip();
        while (buffer.hasRemaining()) {
            channel.write(buffer);
        }

        long position = channel.position();
        System.out.println(position);

        file.close();

    }


}
