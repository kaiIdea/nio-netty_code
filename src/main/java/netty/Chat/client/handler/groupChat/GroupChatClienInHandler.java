package netty.Chat.client.handler.groupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupChatResponseMessage;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 09:46
 * @Description:
 */
public class GroupChatClienInHandler extends SimpleChannelInboundHandler<GroupChatResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupChatResponseMessage msg) throws Exception {
        System.out.println(msg.getContent());
    }
}
