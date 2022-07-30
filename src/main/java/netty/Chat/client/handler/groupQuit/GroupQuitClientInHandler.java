package netty.Chat.client.handler.groupQuit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.GroupCreateResponseMessage;
import netty.Chat.message.GroupQuitResponseMessage;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 08:37
 * @Description:
 */
public class GroupQuitClientInHandler extends SimpleChannelInboundHandler<GroupQuitResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupQuitResponseMessage msg) throws Exception {
        System.out.println(msg.getResult());
    }
}
