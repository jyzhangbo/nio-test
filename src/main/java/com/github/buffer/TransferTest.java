package com.github.buffer;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Author:zhangbo
 * @Date:2019/1/10 16:50
 */
public class TransferTest {

    public static void main(String[] args) throws Exception{
        transferFrom();
        transferTo();
    }

    public static void transferTo() throws Exception{
        RandomAccessFile file1 =new RandomAccessFile("zhangbo.text","rw");
        FileChannel channelFrom = file1.getChannel();

        RandomAccessFile file2 =new RandomAccessFile("zhangbo3.text","rw");
        FileChannel channelTo = file2.getChannel();

        long position = 0;
        long count = channelFrom.size();
        channelFrom.transferTo(position,count,channelTo);

        file1.close();
        file2.close();
    }

    public static void transferFrom() throws Exception{
        RandomAccessFile file1 =new RandomAccessFile("zhangbo.text","rw");
        FileChannel channelFrom = file1.getChannel();

        RandomAccessFile file2 =new RandomAccessFile("zhangbo2.text","rw");
        FileChannel channelTo = file2.getChannel();

        long position = 0;
        long count = channelFrom.size();
        channelTo.transferFrom(channelFrom,position,count);

        file1.close();
        file2.close();
    }

}
