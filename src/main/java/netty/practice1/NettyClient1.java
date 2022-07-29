package netty.practice1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/25 17:07
 * @Description:
 */
public class NettyClient1 {

    public static Logger log = LoggerFactory.getLogger(NettyClient1.class);

//    public static void main(String[] args) throws InterruptedException {
//        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
//        try {
//            ChannelFuture channelFuture = new Bootstrap().group(loopGroup)
//                    .channel(NioSocketChannel.class)
//                    .handler(new ChannelInitializer<NioSocketChannel>() {
//                        @Override
//                        protected void initChannel(NioSocketChannel ch) throws Exception {
//                            ch.pipeline().addLast(new NettyClientHandler1());
//                        }
//                    })
//                    .connect("127.0.0.1", 8889).sync();
//            Channel channel = channelFuture.channel();
//            System.out.println();
//            channel.closeFuture().sync();
//        } finally {
//            loopGroup.shutdownGracefully();
//        }
//    }

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
//        try {
//        } finally {
//            loopGroup.shutdownGracefully();
//        }

        ChannelFuture channelFuture = new Bootstrap().group(loopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        //ch.pipeline().addLast(new NettyClientHandler1());
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                //connect为异步非阻塞，main线程发起connect调用，但是真正执行方法执行的是nio线程
                //main线程不关心结果，继续执行下面业务。

                .connect("127.0.0.1", 8889);
        //所以主线程必须同步阻塞获取连接，才能继续执行
        //否则没有建立连接，获取channel对象会有问题
        //阻塞住当前线程，直到nio线程连接建立完毕
        //channelFuture.sync();

        channelFuture.addListener(new ChannelFutureListener() {
            //使用addListener方法异步处理结果

            //异步处理，交给Nio线程执行方法，main线程不做等待
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                Channel channel = channelFuture.channel();
                log.info("channel {}",channel);
                channel.writeAndFlush("hello server,I'm client");
                System.out.println();
            }
        });
        //主线程或许已经执行完毕
        log.info("***main");
    }
}
