package netty.Chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.channel.embedded.EmbeddedChannel;
import netty.Chat.server.TestChatHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Auther: YangKai
 * @Date: 2022/7/29 18:58
 * @Description:
 */
public class TestChatHandler1 {

    public static Logger log = LoggerFactory.getLogger(TestChatHandler1.class);

    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                new ChannelOutboundHandlerAdapter(){
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        log.info("1");
                        //ctx.channel().writeAndFlush("haode");
                        super.write(ctx, msg, promise);
                    }
                },
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.info("a");
                        super.channelRead(ctx, msg);
                    }
                },
                new ChannelOutboundHandlerAdapter(){
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        log.info("2");
                        super.write(ctx, msg, promise);
                    }
                },
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.info("b");
                        ctx.channel().writeAndFlush("nihao");
                        //super.channelRead(ctx, msg);
                    }
                },
                new ChannelOutboundHandlerAdapter(){
                    @Override
                    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
                        log.info("3");
                        super.write(ctx, msg, promise);
                    }
                },
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.info("c");
                        super.channelRead(ctx, msg);
                    }
                }
        );
        //channel.writeOutbound("hello world");
        channel.writeInbound("hello world");
    }
}
