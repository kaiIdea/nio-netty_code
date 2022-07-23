package nonBlocking;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/7/22 11:22
 * @Description:
 */
public class NonBlockingServer implements Runnable{
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
        //打开服务
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口号
        ssc.bind(new InetSocketAddress(7888));
        System.out.println(server_log+"服务启动完成");
        //设置非阻塞
        ssc.configureBlocking(false);

        ByteBuffer buffer = ByteBuffer.allocate(200);


        ByteBuffer resultBuffer = ByteBuffer.allocate(1024);
        //将通道注册到选择器
        Selector selector = Selector.open();
        //将服务注册到selector，以便后续可以根据选择器ID,获取所有事件
        ssc.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //返回有事件发生的个数，没有事件发生时，程序会在这里阻塞
            int i = selector.select();
            if(i <= 0){
                continue;
            }
            //获取发生事件的集合
            Set<SelectionKey> keys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keys.iterator();
            while (iterator.hasNext()){
                SelectionKey sk = iterator.next();
                //判断事件类型
                if(sk.isAcceptable()){
                    //通过选择器获取,新的客户端链接
                    //服务端再接收链接请求后，会返回一个新的socket链接
                    SocketChannel sc = ssc.accept();
                    //设置为非阻塞
                    sc.configureBlocking(false);
                    //注册到事件监听器
                    sc.register(selector,SelectionKey.OP_READ);
                    System.out.println(server_log+"请求连接完成.");
                    System.out.println(server_log+"RemoteAddress:"+sc.getRemoteAddress());
                }
                if (sk.isReadable()) {
                    buffer.clear();
                    System.out.println(server_log+"获取client请求数据.");
                    SocketChannel sc = (SocketChannel) sk.channel();
                    sc.read(buffer);
                    buffer.flip();
                    String aa = new String(buffer.array());
                    System.out.println(server_log+"成功接收client："+aa);

                    resultBuffer.clear();
                    String result = "client:"+aa;
                    System.out.println("[server txt:]"+result);
                    resultBuffer.put(result.getBytes());
                    resultBuffer.flip();
                    sc.write(resultBuffer);

                }
                //移除已经处理过的事件
                iterator.remove();
            }

        }
    }

    public static void main(String[] args) {
        new Thread(new NonBlockingServer()).start();
    }
}
