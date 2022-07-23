package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: YangKai
 * @Date: 2022/7/21 08:16
 * @Description:
 */
public class Client {

    public static void client() throws IOException {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",9888));
        sc.configureBlocking(false);
        String str = "nihaoya";
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        sc.write(byteBuffer);
        sc.close();
    }

    public static void main(String[] args) throws IOException {
        client();
        System.out.println();
    }
}
