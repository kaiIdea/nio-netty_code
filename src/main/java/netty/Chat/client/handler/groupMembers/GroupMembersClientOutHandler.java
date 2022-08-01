package netty.Chat.client.handler.groupMembers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.StringUtil;
import netty.Chat.message.ChooseMenuRequestMessage;
import netty.Chat.message.GroupJoinRequestMessage;
import netty.Chat.message.GroupMembersRequestMessage;

import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 07:12
 * @Description:加入群聊
 */
public class GroupMembersClientOutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object message, ChannelPromise promise) throws Exception {
        if (!(message instanceof GroupMembersRequestMessage)) {
            super.write(ctx, message, promise);
            return;
        }
        GroupMembersRequestMessage members = (GroupMembersRequestMessage) message;
        String userName = members.getUserName();
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            while (true) {
                System.out.println("==================================");
                System.out.println("members [group name]");
                System.out.println("qm 退出当前菜单目录");
                System.out.println("==================================");
                String line = scanner.nextLine();
                if(StringUtil.isNullOrEmpty(line)){
                    continue;
                }
                String[] s = line.split(" ");
                if ("members".equals(s[0])) {
                    String groupName = s[1];
                    members.setGroupName(groupName);
                    ctx.writeAndFlush(members);
                }
                if ("qm".equals(s[0])) {
                    ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(userName));
                    break;
                }
            }
        },"group join").start();
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
