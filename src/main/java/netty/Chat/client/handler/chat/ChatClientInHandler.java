package netty.Chat.client.handler.chat;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.ChatResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 14:56
 * @Description:打印单聊发送的消息内容
 */
@ChannelHandler.Sharable
public class ChatClientInHandler extends SimpleChannelInboundHandler<ChatResponseMessage> {
    public static Logger log = LoggerFactory.getLogger(ChatClientInHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatResponseMessage msg) throws Exception {
        if(!msg.isSuccess()){
            System.out.println("【"+msg.getResult()+"】");
            return;
        }
        System.out.println(msg.getContent());
    }
}
