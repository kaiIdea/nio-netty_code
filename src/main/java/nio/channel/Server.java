package nio.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/7/21 08:16
 * @Description:
 */
public class Server {

    public static void server() throws IOException {
        //开启服务
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口号
        ssc.bind(new InetSocketAddress(9888));
        //开启非阻塞模式
        ssc.configureBlocking(false);
        //创建选择器
        Selector selector = Selector.open();
        //服务端的ServerSocketChannel也要注册到选择器上
        ssc.register(selector,SelectionKey.OP_ACCEPT);
        System.out.println("服务启动成功");

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true){
            //返回有事件发生的通道个数
            int selectResult = selector.select();
            if(selectResult < 0){
                System.out.println("[server:]暂时没有链接.");
            }
            //获取事件集合
            Set<SelectionKey> keys = selector.keys();
            //开始进行事件遍历处理
            Iterator<SelectionKey> sk = keys.iterator();
            while (sk.hasNext()){
                SelectionKey key = sk.next();
                //请求链接事件处理
                if(key.isAcceptable()){
                    //ServerSocketChannel.accept()方法会接受客户端的链接请求，并且返回新的客户端链接通道
                    //注意，如果此时没有对应的客户端，会返回null
                    SocketChannel sc = ssc.accept();
                    System.out.println("[server:]收到客户端的链接请求："+sc.getRemoteAddress());
                    //设置新的客户端链接通道为非阻塞模式
                    sc.configureBlocking(false);
                    //既然是新的。那就需要重新注册selector
                    sc.register(selector,SelectionKey.OP_READ);
                }
                //读取事件处理
                if(key.isReadable()){
                    SocketChannel sc = (SocketChannel) key.channel();
                    //将客户端传来的数据，放入缓冲区
                    sc.read(buffer);
                    System.out.println("[server:]收到客户端的消息内容为："+new String(buffer.array()));
                    //定制返回给客户端消息
                    sc.write(ByteBuffer.wrap("server已收到消息，谢谢.".getBytes()));
                }
                //把已经处理过的事件进行清楚，防止重复处理
                //不然的话，对于连接的请求，服务断还是回去accept产生一个SocketChannel，但是此时没有客户端来对接，就会返回一个null
                sk.remove();
            }


        }
    }

    public static void main(String[] args) throws IOException {
        server();
        System.out.println();
    }
}
