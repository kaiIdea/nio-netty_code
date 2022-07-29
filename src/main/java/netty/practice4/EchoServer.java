package netty.practice4;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 10:25
 * @Description:
 */
public class EchoServer {

    public static Logger log = LoggerFactory.getLogger(EchoServer.class);

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ChannelFuture channelFuture = new ServerBootstrap()
                .group(eventLoopGroup)
                //指定所使用的NIO传输Channel
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("1");
//                                ByteBuf buf = (ByteBuf) msg;
//                                String messge = buf.toString(CharsetUtil.UTF_8);
                                String aa = msg.toString();
                                log.info("server接收到："+aa);
                                ctx.writeAndFlush(aa);
                            }
                        });
                    }
                }).bind(8080);

        //入站：LoggingHandler -> StringDecoder -> ChannelInboundHandler
        //出战：LoggingHandler <- StringEncoder
        channelFuture.sync();
        log.info("server启动完成");
        Channel channel = channelFuture.channel();
        //获取channel的CloseFuture，并且阻塞当前线程直到完成
        channel.closeFuture().sync();
    }
}
