package netty.protocl;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 15:22
 * @Description:
 */
public class TestHttp {

    public static Logger log = LoggerFactory.getLogger(TestHttp.class);

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup eventGroupLoop = new NioEventLoopGroup();
        try {
            Channel channel =  new ServerBootstrap()
                    .group(eventGroupLoop)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new LoggingHandler());
                            ch.pipeline().addLast(new HttpServerCodec());
                            ch.pipeline().addLast(new SimpleChannelInboundHandler<HttpRequest>() {
                                @Override
                                protected void channelRead0(ChannelHandlerContext ctx, HttpRequest msg) throws Exception {
                                    //返回响应
                                    DefaultFullHttpResponse response = new DefaultFullHttpResponse(
                                            msg.protocolVersion(),HttpResponseStatus.OK
                                    );
                                    byte[] bytes = "<h1> accept message.Thank you </h1>".getBytes();
                                    response.headers().setInt(CONTENT_LENGTH,bytes.length);
                                    response.content().writeBytes(bytes);
                                    ctx.writeAndFlush(response);
                                }
                            });
//                            ch.pipeline().addLast(new ChannelInboundHandlerAdapter(){
//                                @Override
//                                public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
//
//                                    if (msg instanceof HttpRequest) {
//                                        log.info("HttpRequest {}",msg);
//                                    }
//                                    if(msg instanceof HttpContent){
//                                        log.info("HttpContent {}",msg);
//                                    }
//                                }
//                            });
                        }
                    })
                    .bind(8080).sync().channel();
            channel.closeFuture().addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    log.info("*********************************服务断开连接**************************************");
                    eventGroupLoop.shutdownGracefully();
                    log.info("关闭循环事件组.");
                }
            });
        } finally {
        }

    }
}
