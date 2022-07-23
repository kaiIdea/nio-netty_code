package channel;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Auther: YangKai
 * @Date: 2022/7/20 15:17
 * @Description:
 */
public class TestFileChannel {



    public static void main(String[] args) throws IOException {
    }


    //1.RandomAccessFile读取文件，获取通道进行IO处理
    public static void test1() throws IOException {
        //读取文件
        RandomAccessFile readFile = new RandomAccessFile("e:/ReadWriteLock.txt","rw");
        //获取通道
        FileChannel channel = readFile.getChannel();
        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(5012);
        //从通道读取数据至buffer
        //如果read返回-1,代表读取结束或者没有数据
        channel.read(buffer);
        //切换模式，将buffer切换为读模式，重置position,limit等属性
        buffer.flip();
        RandomAccessFile writeFile = new RandomAccessFile("e:/1.txt", "rw");
        //将Buffer数据写入新的文件，
        writeFile.write(buffer.array());
        writeFile.close();
        readFile.close();
        //关闭通道
        channel.close();
        System.out.println();
    }


    //2.通过流的形式读取文件数据，并且写入一个新的文件
    public static void test2() throws IOException {
        FileInputStream stream = new FileInputStream("e:/ReadWriteLock.txt");
        //获取通道
        FileChannel channel = stream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1824);
        //int返回代表多少字节读取到Buffer中，如果返回-1代表文件读取已经到末尾
        int result = channel.read(buffer);



        //读取数据至Buffer后，必须要切换模式。
        buffer.flip();
        FileOutputStream outputStream = new FileOutputStream("e:/2.txt");
        outputStream.write(buffer.array());
        channel.close();
        stream.close();
        outputStream.close();
    }


    //3.指定字符串，写入Buffer，并且写入新的文件保存
    public static void test3() throws IOException {
        String str = "指定字符串，写入Buffer，并且写入新的文件保存2";
        RandomAccessFile file = new RandomAccessFile("e:/3.txt","rw");
        //获取通道
        FileChannel channel = file.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //将指定字符串，写入Buffer
        buffer.put(str.getBytes());

        //buffer模式切换
        buffer.flip();
        while (buffer.hasRemaining()){
            //开始读buffer数据，写入文件
            channel.write(buffer);
        }

        //关闭通道
        channel.close();
        file.close();
        //情况缓冲区，看了下源码，清空缓冲区并不会真正的清楚数据，
        //这是将position,limit等属性进行重置，limit == capacity,position==0
        buffer.clear();
    }
    //4.通道与通道之间数据传输
    public static void test4() throws IOException {
        RandomAccessFile file1 = new RandomAccessFile("e:/3.txt", "rw");
        FileChannel channel1 = file1.getChannel();
        //ByteBuffer buffer1 = ByteBuffer.allocate(1024);
        //int result1 = channel1.read(buffer1);

        RandomAccessFile file2 = new RandomAccessFile("e:/5.txt", "rw");
        FileChannel channel2 = file2.getChannel();
        //channel1.transferTo(0,channel1.size(),channel2);
        channel2.transferFrom(channel1,0,channel1.size());

        channel1.close();
        channel2.close();
    }
}
