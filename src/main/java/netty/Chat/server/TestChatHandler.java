package netty.Chat.server;

import io.netty.channel.*;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LoggingHandler;
import netty.Chat.client.ChatClientInHandler;
import netty.Chat.client.ChatClientOutHandler;
import netty.Chat.client.LoginClientInHandler;
import netty.Chat.client.LoginClientOutHandler;
import netty.Chat.message.ChatRequestMessage;
import netty.Chat.message.LoginRequestMessage;
import netty.Chat.message.LoginResponseMessage;
import netty.Chat.protocol.MessageCodecShare;
import netty.Chat.protocol.ProcotolFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Auther: YangKai
 * @Date: 2022/7/29 10:32
 * @Description:
 */
public class TestChatHandler {

    public static Logger log = LoggerFactory.getLogger(TestChatHandler.class);

    public static void main(String[] args) {
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecShare messageCodec = new MessageCodecShare();
        EmbeddedChannel channel = new EmbeddedChannel(
                new ProcotolFrameDecoder(),
                loggingHandler,
                messageCodec,
                new LoginClientInHandler(),
                //new LoginClientOutHandler(),
                new ChatClientInHandler(),
                new ChatClientOutHandler()
        );
        LoginResponseMessage message = new LoginResponseMessage(true, "登录成功","zhangsan");
        channel.writeInbound(message);

        ChatRequestMessage chatRequestMessage = new ChatRequestMessage("zhangsan", "lisi", "nihao");
        //channel.writeOutbound(chatRequestMessage);
        channel.writeAndFlush(chatRequestMessage);
    }
}
