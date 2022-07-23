package blocking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Auther: YangKai
 * @Date: 2022/7/22 09:18
 * @Description:
 */
public class BlockingServer implements Runnable{

    public static String server_log = "[server:]";
    @Override
    public void run() {
        try {
            server();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public static void main(String[] args) {
        new Thread(new BlockingServer()).start();
        new Thread(new BlockingClient1()).start();
        new Thread(new BlockingClient2()).start();
    }
}
