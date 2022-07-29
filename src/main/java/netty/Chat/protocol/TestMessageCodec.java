package netty.Chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import netty.Chat.message.LoginRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 17:55
 * @Description:
 */
public class TestMessageCodec {
    public static Logger log = LoggerFactory.getLogger(TestMessageCodec.class);

    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LoggingHandler(),
                new LengthFieldBasedFrameDecoder(
                        1024,12,4,0,0
                ),
                new MessageCodec()
        );
        LoginRequestMessage loginRequestMessage = new LoginRequestMessage("zhangsan", "123456");
        channel.writeOutbound(loginRequestMessage);

        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,loginRequestMessage,buf);

        channel.writeInbound(buf);
    } 
}
