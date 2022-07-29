package netty.practice1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/25 17:07
 * @Description:
 */
public class NettyServer1 {

    public static Logger log = LoggerFactory.getLogger(NettyServer1.class);

    public static void main(String[] args) throws InterruptedException {


        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        log.info("创建事件循环组success.");
        ChannelFuture channelFuture = new ServerBootstrap().group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline()
                                .addLast(new LoggingHandler())
                                //.addLast(new StringEncoder())
                                .addLast(new NettyServerHandler1())
                                .addLast(new NettyServerHandler2())
                                .addLast(new NettyServerHandler3());
                    }
                })
                .bind(8889);
//        //建立连接，阻塞获取连接结果
//        log.info("waiting connect...");
//        channelFuture.sync();
//        log.info("waiting channel...");
//        //获取通道
//        Channel channel = channelFuture.channel();
//        log.info("channel{}",channel);
//        //关闭
//        channel.closeFuture().sync();


//        try {
//        } finally {
//            //优雅关闭，事件循环组
//            //不在接收新的任务，等已经接收的任务执行完成，然后关闭组或者说线程池
//            eventLoopGroup.shutdownGracefully();
//        }

    }
}
