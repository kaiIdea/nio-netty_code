package netty.Chat.client.handler.groupMembers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupJoinResponseMessage;
import netty.Chat.message.GroupMembersResponseMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 08:37
 * @Description:
 */
public class GroupMembersClientInHandler extends SimpleChannelInboundHandler<GroupMembersResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMembersResponseMessage message) throws Exception {
        if(!message.isSuccess()){
            System.out.println(message.getResult());
            return;
        }
        Object[] objects = message.getMembers().toArray();
        System.out.println(Arrays.toString(objects));
    }
}
