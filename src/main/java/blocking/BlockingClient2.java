package blocking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Auther: YangKai
 * @Date: 2022/7/22 09:19
 * @Description:
 */
public class BlockingClient2 implements Runnable{
    public static String client_log = "[client2:]";
    @Override
    public void run() {
        try {
            client2();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
