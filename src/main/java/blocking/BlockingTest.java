package blocking;

import channel.Server;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;

/**
 * @Auther: YangKai
 * @Date: 2022/7/22 07:17
 * @Description:
 */
public class BlockingTest {

    public static String server_log = "[server:]";

    public static String client_log = "[client:]";

    @Test
    public void server() throws IOException {
        //打开服务并且绑定端口号
        ServerSocket ss = new ServerSocket(9888);
        System.out.println(server_log+"服务启动完成");
        while (true){
            //接收客户端请求，
            Socket socket = ss.accept();
            System.out.println(server_log+"接收请求."+socket.getRemoteSocketAddress());
            System.out.println(server_log+"线程Name:"+Thread.currentThread().getName());
            //获取客户端链接中的流数据
            InputStream stream = socket.getInputStream();
            byte[] bytes = new byte[50];
            int len = stream.read(bytes);
            System.out.println(server_log+new String(bytes));

            //返回通知给客户端
            OutputStream outputStream = socket.getOutputStream();
            //将通知写入返回流
            outputStream.write("server message accept success.".getBytes());
            //强制刷新缓存
            outputStream.flush();
            //以下关闭处理
            stream.close();
            outputStream.close();
            socket.close();
        }
    }


    @Test
    public void client1() throws IOException {
        //打开客户端，设置请求IP跟端口
        Socket socket = new Socket("127.0.0.1",9888);
        System.out.println(client_log+"建立完成");
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("I'm client1, hello Server!".getBytes());
        System.out.println(client_log+"消息发送成功");
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[50];
        int len = inputStream.read(bytes);
        System.out.println(client_log+"服务端返回通知内容："+new String(bytes));
        System.out.println();
        inputStream.close();
        socket.close();
    }

    @Test
    public void client2() throws IOException {
        //打开客户端，设置请求IP跟端口
        Socket socket = new Socket("127.0.0.1",9888);
        System.out.println(client_log+"建立完成");
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("I'm client2, hello Server!".getBytes());
        System.out.println(client_log+"消息发送成功");
        InputStream inputStream = socket.getInputStream();
        byte[] bytes = new byte[50];
        int len = inputStream.read(bytes);
        System.out.println(client_log+"服务端返回通知内容："+new String(bytes));
        System.out.println();
        inputStream.close();
        socket.close();
    }
}
