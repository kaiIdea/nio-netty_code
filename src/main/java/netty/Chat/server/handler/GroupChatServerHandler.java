package netty.Chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupChatRequestMessage;
import netty.Chat.message.GroupChatResponseMessage;
import netty.Chat.server.session.GroupSessionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 09:59
 * @Description:
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<GroupChatRequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatRequestMessage msg) throws Exception {
        String from = msg.getFrom();
        String content = msg.getContent();
        String groupName = msg.getGroupName();
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        Iterator<Channel> iterator = channels.iterator();
        while (iterator.hasNext()){
            Channel channel = iterator.next();
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+" - ";
            channel.writeAndFlush(
                    new GroupChatResponseMessage(from,time+from+": "+content));
        }
    }
}
