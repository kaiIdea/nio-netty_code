package netty.Chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupCreateRequestMessage;
import netty.Chat.message.GroupCreateResponseMessage;
import netty.Chat.message.GroupQuitRequestMessage;
import netty.Chat.message.GroupQuitResponseMessage;
import netty.Chat.server.session.Group;
import netty.Chat.server.session.GroupSessionFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 08:26
 * @Description:
 */
public class GroupQuitServerHandler extends SimpleChannelInboundHandler<GroupQuitRequestMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitRequestMessage message) throws Exception {
        String groupName = message.getGroupName();
        String username = message.getUsername();
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(groupName);
        Group group = GroupSessionFactory.getGroupSession().removeMember(groupName, username);
        if(null == group){
            ctx.writeAndFlush(new GroupQuitResponseMessage(false,"群组或者成员不存在"));
            return;
        }
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+" - "+username;
        GroupQuitResponseMessage responseMessage = new GroupQuitResponseMessage(true,time+" 退出群聊");
        Iterator<Channel> iterator = channels.iterator();
        while (iterator.hasNext()){
            Channel channel = iterator.next();
            channel.writeAndFlush(responseMessage);
        }
    }
}
