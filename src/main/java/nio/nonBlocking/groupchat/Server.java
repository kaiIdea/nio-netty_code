package nio.nonBlocking.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @Auther: YangKai
 * @Date: 2022/7/23 10:04
 * @Description:聊天室服务端
 */
public class Server {


    private ServerSocketChannel ssc;
    private Selector selector;
    private final int port = 9888;

    public Server() throws IOException {
        this.selector = Selector.open();
        this.ssc = ServerSocketChannel.open();
        this.ssc.bind(new InetSocketAddress(port));
        this.ssc.configureBlocking(false);
        this.ssc.register(selector,SelectionKey.OP_ACCEPT);
     }

    public void server(){
        try {
            while (true){
                int selectCount = selector.select();
                if(selectCount <= 0){
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()){
                    SelectionKey sk = iterator.next();
                    if(sk.isAcceptable()){
                        SocketChannel accept = ssc.accept();
                        accept.configureBlocking(false);
                        accept.register(selector,SelectionKey.OP_READ);
                    }
                    if (sk.isReadable()) {
                        //读取数据
                        //转发
                        readData(sk);
                    }
                    iterator.remove();
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData(SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.channel();
        ByteBuffer buffer = ByteBuffer.allocate(200);
        sc.read(buffer);
        String message = new String(buffer.array());
        System.out.println(message);
        dispatchMessage(message,sc);
    }

    private void dispatchMessage(String msg,SocketChannel channel){

        Iterator<SelectionKey> iterator = selector.keys().iterator();
        try {
            while (iterator.hasNext()){
                SelectionKey key = iterator.next();
                SocketChannel sc = (SocketChannel) key.channel();
                if(sc instanceof SocketChannel && sc != channel){
                    sc.write(ByteBuffer.wrap(msg.getBytes()));
                }
            }
        } catch (IOException e) {
            System.out.println("有人下线...");
        }
    }
}
