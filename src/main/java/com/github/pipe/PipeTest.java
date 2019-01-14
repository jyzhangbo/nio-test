package com.github.pipe;

import java.nio.ByteBuffer;
import java.nio.channels.Pipe;

/**
 * @Author:zhangbo
 * @Date:2019/1/14 10:30
 */
public class PipeTest {

    public static void main(String[] args) throws Exception{
        Pipe pipe =Pipe.open();
        Pipe.SinkChannel sink = pipe.sink();

        ByteBuffer byteBuffer = ByteBuffer.allocate(16);
        byteBuffer.clear();
        byteBuffer.put("zhangbo".getBytes());
        byteBuffer.flip();
        sink.write(byteBuffer);

        Pipe.SourceChannel source = pipe.source();
        ByteBuffer buffer = ByteBuffer.allocate(48);
        int len = source.read(buffer);

        System.out.println(new String(buffer.array(),0,len));

        sink.close();
        source.close();

    }

}
