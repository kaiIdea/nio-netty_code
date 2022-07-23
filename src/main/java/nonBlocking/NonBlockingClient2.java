package nonBlocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/22 11:22
 * @Description:
 */
public class NonBlockingClient2 implements Runnable{


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
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",7888));
        sc.configureBlocking(false);
        ByteBuffer sendBuffer = ByteBuffer.allocate(200);
        ByteBuffer acceptBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            acceptBuffer.clear();
            sendBuffer.clear();
            String str = scanner.next();
            sendBuffer.put(str.getBytes());
            sendBuffer.flip();
            sc.write(sendBuffer);



            sc.read(acceptBuffer);
            acceptBuffer.flip();
            String ss = new String(acceptBuffer.array());
            acceptBuffer.clear();
            System.out.println("[server:]"+ss);
        }
        sc.close();
    }

    public static void main(String[] args) {
        new Thread(new NonBlockingClient2()).start();
    }
}
