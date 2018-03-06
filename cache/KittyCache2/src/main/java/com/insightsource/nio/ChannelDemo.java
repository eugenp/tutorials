package com.insightsource.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelDemo {

    public static void main(String[] args) {
        try {
            System.out.println("=============");

            RandomAccessFile aFile = new RandomAccessFile("d:\\data.txt", "rw");
            FileChannel aChannel = aFile.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(48);

            int bytesRead = aChannel.read(byteBuffer);
            while (bytesRead != -1) {
                System.out.println("Read : " + bytesRead);
                byteBuffer.flip();    //first write to buffer, then reverse to get from buffer !!

                while (byteBuffer.hasRemaining()) {
                    System.out.println((char) byteBuffer.get());
                }

                byteBuffer.clear();
                bytesRead = aChannel.read(byteBuffer);
            }

            aFile.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

}
