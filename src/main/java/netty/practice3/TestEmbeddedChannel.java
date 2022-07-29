package netty.practice3;


import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/26 09:52
 * @Description:
 */
public class TestEmbeddedChannel {

    public static Logger log = LoggerFactory.getLogger(TestEmbeddedChannel.class);

    public static void main(String[] args) {

        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.info("1");
                        super.channelRead(ctx, msg);
                    }
                },
                new ChannelInboundHandlerAdapter(){
                    @Override
                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                        log.info("2");
                        super.channelRead(ctx, msg);
                    }
                }
        );
        channel.writeInbound("hello");
    }
}
