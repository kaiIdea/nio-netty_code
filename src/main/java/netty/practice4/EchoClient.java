package netty.practice4;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;
import netty.practice3.NettyClient3;
import nio.charSet.CharSetTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 10:25
 * @Description:
 */
public class EchoClient {

    public static Logger log = LoggerFactory.getLogger(EchoClient.class);


    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap().group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new StringEncoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new StringDecoder(CharsetUtil.UTF_8));
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                ByteBuf buf = ctx.alloc().buffer();
                                buf = buf.writeBytes("hello,server".getBytes());
                                ctx.writeAndFlush(buf);
                            }

                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//                                ByteBuf byteBuf = (ByteBuf) msg;
//                                log.info(byteBuf.toString(CharsetUtil.UTF_8));
                                log.info(msg.toString());
                            }
                        });
                    }
                })
                .connect("127.0.0.1", 8080).sync();
                log.info("client启动完成");
        Channel channel = channelFuture.channel();
        System.out.println();
        channel.closeFuture().sync();
    }
}
