package netty.Chat.client.handler.groupCreate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupCreateResponseMessage;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 08:37
 * @Description:
 */
public class GroupCreateClienInHandler extends SimpleChannelInboundHandler<GroupCreateResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupCreateResponseMessage msg) throws Exception {
        System.out.println(msg.getResult());
    }
}
