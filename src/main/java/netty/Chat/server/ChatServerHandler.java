package netty.Chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.ChatRequestMessage;
import netty.Chat.message.ChatResponseMessage;
import netty.Chat.server.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 14:54
 * @Description:
 */
public class ChatServerHandler extends SimpleChannelInboundHandler<ChatRequestMessage> {

    public static Logger log = LoggerFactory.getLogger(ChatServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatRequestMessage msg) throws Exception {
        log.info("【ChatServerHandler.channelRead0】{}",msg);
        String from = msg.getFrom();
        String to = msg.getTo();
        Channel channel1 = SessionFactory.getSession().getChannel(from);
        Channel toChannel = SessionFactory.getSession().getChannel(to);
        if(null == toChannel || !toChannel.isActive()){
            ChatResponseMessage responseMessage = new ChatResponseMessage(
                    false, to + ":不在线，发送失败",from);
            channel1.writeAndFlush(responseMessage);
        }
        System.out.println();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+" - ";
        toChannel.writeAndFlush(new ChatResponseMessage(from,time+from+": "+msg.getContent()));
    }

    public static void main(String[] args) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        System.out.println();
    }
}
