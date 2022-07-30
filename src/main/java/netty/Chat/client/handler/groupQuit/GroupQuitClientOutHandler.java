package netty.Chat.client.handler.groupQuit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import netty.Chat.message.ChooseMenuRequestMessage;
import netty.Chat.message.GroupCreateRequestMessage;
import netty.Chat.message.GroupQuitRequestMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 07:12
 * @Description:创建群聊菜单
 */
public class GroupQuitClientOutHandler extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(!(msg instanceof GroupQuitRequestMessage)){
            super.write(ctx, msg, promise);
            return;
        }
        GroupQuitRequestMessage message = (GroupQuitRequestMessage) msg;
        String username = message.getUsername();
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            while (true){
                System.out.println("==================================");
                System.out.println("quit [group name]");
                System.out.println("qm 退出当前菜单目录");
                System.out.println("==================================");
                String line = scanner.nextLine();
                String[] s = line.split(" ");
                if("quit".equals(s[0])){
                    message.setGroupName(s[1]);
                    ctx.writeAndFlush(message);
                }
                //退出当前菜单
                if("qm".equals(s[0])){
                    ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(username));
                    break;
                }
            }
        },"GroupCreate").start();
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
