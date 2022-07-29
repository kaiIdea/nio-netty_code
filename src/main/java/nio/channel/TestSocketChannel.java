package nio.channel;

import nio.charSet.CharSetTest;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

/**
 * @Auther: YangKai
 * @Date: 2022/7/20 17:33
 * @Description:
 */
public class TestSocketChannel extends CharSetTest {


    public static void server() throws Exception {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口号，
        ssc.bind(new InetSocketAddress(9898));
        //设置非阻塞模式
        //ssc.configureBlocking(false);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true){
            //获取客户端连接通道
            SocketChannel sc = ssc.accept();
            if(sc != null){
                sc.read(buffer);
                System.out.println(new String(buffer.array()));
                sc.write(ByteBuffer.wrap("知识就是力量.".getBytes()));
                sc.close();
            }

        }
    }
    public static void main(String[] args) throws Exception {
        server();
        System.out.println();
    }
}
