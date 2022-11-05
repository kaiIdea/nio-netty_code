package netty.Chat.client.handler.groupJoin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupCreateResponseMessage;
import netty.Chat.message.GroupJoinResponseMessage;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 08:37
 * @Description:
 */
public class GroupJoinClientInHandler extends SimpleChannelInboundHandler<GroupJoinResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupJoinResponseMessage msg) throws Exception {
        System.out.println(msg.getResult());
    }
}
