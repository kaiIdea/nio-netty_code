package netty.practice1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/25 20:30
 * @Description:
 */
public class CloseFutureClient {

    public static Logger log = LoggerFactory.getLogger(CloseFutureClient.class);

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap()
                .group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect("127.0.0.1", 8889);
        Channel channel = channelFuture.sync().channel();
        log.info("channel:{}",channel);
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String text = scanner.next();
                if("q".endsWith(text)){
                    //close方法也是异步的。
                    //所以会存在一种问题，那就是channel还未真正关闭，
                    //连接已经关闭，日志已经打印
                    //注意，打印日志线程，跟关闭channel线程不是同一个线程(nio线程)。
                    channel.close();
                    break;
                }
                channel.writeAndFlush(text);


            }
        },"input").start();

        //channel.closeFuture()

        ChannelFuture closeFuture = channel.closeFuture();
//        log.info("wait close...");
//        closeFuture.sync();
//        log.info("close success...");

        closeFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                log.info("close success...");
                eventLoopGroup.shutdownGracefully();
            }
        });



    }
}
