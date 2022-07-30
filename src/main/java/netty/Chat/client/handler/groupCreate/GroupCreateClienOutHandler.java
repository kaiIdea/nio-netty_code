package netty.Chat.client.handler.groupCreate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import netty.Chat.message.ChooseMenuRequestMessage;
import netty.Chat.message.GroupCreateRequestMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 07:12
 * @Description:创建群聊菜单
 */
public class GroupCreateClienOutHandler extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(!(msg instanceof GroupCreateRequestMessage)){
            super.write(ctx, msg, promise);
            return;
        }
        GroupCreateRequestMessage message = (GroupCreateRequestMessage) msg;
        String from = message.getFrom();
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
            while (true){
                System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
                System.out.println("==================================");
                System.out.println("gcreate [group name] [m1,m2,m3...]");
                System.out.println("qm 退出当前菜单目录");
                System.out.println("==================================");
                String line = scanner.nextLine();
                String[] s = line.split(" ");
                if("gcreate".equals(s[0])){
                    message.setGroupName(s[1]);
                    HashSet<String> set = new HashSet<>(Arrays.asList(s[2]));
                    message.setMembers(set);
                    ctx.writeAndFlush(message);
                }
                //退出当前菜单
                if("qm".equals(s[0])){
                    ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(from));
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
