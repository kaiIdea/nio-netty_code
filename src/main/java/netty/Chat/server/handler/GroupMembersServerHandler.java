package netty.Chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupJoinRequestMessage;
import netty.Chat.message.GroupJoinResponseMessage;
import netty.Chat.message.GroupMembersRequestMessage;
import netty.Chat.message.GroupMembersResponseMessage;
import netty.Chat.server.session.GroupSessionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/8/1 16:40
 * @Description:查询群组成员信息
 */
public class GroupMembersServerHandler extends SimpleChannelInboundHandler<GroupMembersRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+" - ";
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        if(null == channels || channels.isEmpty()){
            ctx.writeAndFlush(new GroupMembersResponseMessage(false,time+"没有查询到："+groupName+"的群聊分组"));
            return;
        }
        Set<String> members = GroupSessionFactory.getGroupSession().getMembers(groupName);
        ctx.writeAndFlush(new GroupMembersResponseMessage(true,members));
    }
}
