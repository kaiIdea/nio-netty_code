package netty.practice3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
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
 * @Date: 2022/7/26 09:02
 * @Description:
 */
public class NettyClient3 {

    public static Logger log = LoggerFactory.getLogger(NettyClient3.class);

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        ChannelFuture channelFuture = new Bootstrap().group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new StringEncoder());
                    }
                }).connect("127.0.0.1", 9888).sync();
        Channel channel = channelFuture.channel();
        log.info("channel:{}",channel);;
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String next = scanner.next();
                channel.writeAndFlush(next);
            }
        }).start();
        log.info("wait client关闭");
        //channel.closeFuture().sync();
        //.close();
        //group.shutdownGracefully();
        //log.info("client关闭 success.");
    }
}
