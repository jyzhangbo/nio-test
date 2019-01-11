package com.github.selector;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Author:zhangbo
 * @Date:2019/1/11 18:30
 */
public class ClientTest {

    public static void main(String[] args) throws Exception {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 8888));

        while (!channel.finishConnect()){
            Thread.sleep(10);
        }

        while (true) {
            Scanner scanner =new Scanner(System.in);
            System.out.print("请输入要发送的内容：");
            String writeStr = scanner.nextLine();
            ByteBuffer byteBuffer = ByteBuffer.allocate(writeStr.length());
            byteBuffer.put(writeStr.getBytes());

            byteBuffer.flip();
            channel.write(byteBuffer);

            byteBuffer.clear();
        }
    }

}
