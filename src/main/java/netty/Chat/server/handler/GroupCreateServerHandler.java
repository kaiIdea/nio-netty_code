package netty.Chat.server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupCreateRequestMessage;
import netty.Chat.message.GroupCreateResponseMessage;
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
public class GroupCreateServerHandler extends SimpleChannelInboundHandler<GroupCreateRequestMessage> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateRequestMessage msg) throws Exception {
        String from = msg.getFrom();
        Set<String> members = msg.getMembers();
        //添加发起者到集合
        members.add(from);
        //将群聊的用户关联channel，加入集合
        GroupSessionFactory.getGroupSession().createGroup(msg.getGroupName(),members);

        //发送消息给各位群聊用户，您已加入群聊
        List<Channel> channels = GroupSessionFactory.getGroupSession().getMembersChannel(msg.getGroupName());
        Iterator<Channel> iterator = channels.iterator();
        while (iterator.hasNext()){
            Channel channel = iterator.next();
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))+" - ";
            channel.writeAndFlush(new GroupCreateResponseMessage(true,
                    time+"您已成功加入群-"+msg.getGroupName()));
        }
    }
}
