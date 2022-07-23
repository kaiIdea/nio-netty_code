package channel;

import charSet.CharSetTest;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Auther: YangKai
 * @Date: 2022/7/21 07:03
 * @Description:
 */
public class TestSocketChannel1 extends CharSetTest {

    public static void client() throws Exception {
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));
        String message = "hello server.";
        sc.write(ByteBuffer.wrap(message.getBytes()));

        ByteBuffer buffer = ByteBuffer.allocate(1024);
        sc.read(buffer);
        buffer.flip();
        System.out.println(new String(buffer.array()));
        sc.close();
    }

    public static void main(String[] args) throws Exception {
        client();
        System.out.println();
    }
}
