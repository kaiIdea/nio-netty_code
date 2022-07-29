package netty.practice3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/26 09:02
 * @Description:
 */
public class NettyServer3 {

    public static Logger log = LoggerFactory.getLogger(NettyServer3.class);

    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new ServerBootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("inHandler1", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("inHandler1");
                                ByteBuf buf = (ByteBuf) msg;
                                super.channelRead(ctx, new User(buf.toString(CharsetUtil.UTF_8)));
                            }
                        });
                        ch.pipeline().addLast("inHandler2", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("inHandler2");
                                User user = (User) msg;
                                log.info("inHandler2 accept User:{}",user);
                                super.channelRead(ctx, msg);
                            }
                        });
                        ch.pipeline().addLast("inHandler3", new ChannelInboundHandlerAdapter() {
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                log.info("inHandler3");
                                ch.writeAndFlush(ctx.alloc().buffer().writeBytes("hello 张三.".getBytes()));
                            }
                        });
                        ch.pipeline().addLast("outHandler4",new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("outHandler4");
                                ByteBuf buf = (ByteBuf) msg;
                                log.info("outHandler4：{}",buf.toString(CharsetUtil.UTF_8));
                            }
                        });
                        ch.pipeline().addLast("outHandler5",new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("outHandler5");
                                super.write(ctx, msg, promise);
                            }
                        });
                        ch.pipeline().addLast("outHandler6",new ChannelOutboundHandlerAdapter() {
                            @Override
                            public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                                log.info("outHandler6");
                                super.write(ctx, msg, promise);
                            }
                        });
                    }
                })
                .bind(9888).sync();

        Channel channel = channelFuture.channel();
        //channel.pipeline()
        log.info("channel:{}",channel);
    }

    static class User{
        public User(String name) {
            this.name = name;
        }
        private String name;

        @Override
        public String toString() {
            return "User{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
