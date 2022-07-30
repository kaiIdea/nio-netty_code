package netty.Chat.client.handler.groupChat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import netty.Chat.message.ChooseMenuRequestMessage;
import netty.Chat.message.GroupChatRequestMessage;

import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 09:46
 * @Description:
 */
public class GroupChatClienOutHandler extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof GroupChatRequestMessage)) {
            super.write(ctx, msg, promise);
            return;
        }
        GroupChatRequestMessage message = (GroupChatRequestMessage) msg;
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            while (true){
                System.out.println("==================================");
                System.out.println("gsend [group name] [content]");
                System.out.println("qm 退出当前菜单目录");
                System.out.println("==================================");
                String line = scanner.nextLine();
                String[] s = line.split(" ");
                if("gsend".equals(s[0])){
                    message.setGroupName(s[1]);
                    message.setContent(s[2]);
                    ctx.writeAndFlush(message);
                }
                if ("qm".equals(s[0])) {
                    ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(message.getFrom()));
                    break;
                }
            }
        },"group chat").start();
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
