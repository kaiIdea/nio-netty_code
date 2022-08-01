package netty.Chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupJoinRequestMessage;
import netty.Chat.message.GroupJoinResponseMessage;
import netty.Chat.server.session.GroupSessionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

/**
 * @Auther: YangKai
 * @Date: 2022/8/1 16:40
 * @Description:单个用户加入群组
 */
public class GroupJoinServerHandler extends SimpleChannelInboundHandler<GroupJoinRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+" - ";
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        if(null == channels || channels.isEmpty()){
            ctx.writeAndFlush(new GroupJoinResponseMessage(false,time+"没有查询到："+groupName+"的群聊分组"));
            return;
        }
        GroupSessionFactory.getGroupSession().joinMember(message.getGroupName(),message.getUsername());
        channels = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        GroupJoinResponseMessage responseMessage = new GroupJoinResponseMessage(false, time + "[" + message.getUsername() + "] 已加入到" +
                message.getGroupName());
        Iterator<Channel> iterator = channels.iterator();
        while (iterator.hasNext()){
            Channel channel = iterator.next();
            channel.writeAndFlush(responseMessage);
        }
    }
}
