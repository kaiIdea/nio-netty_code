package netty.Chat.client.handler.groupJoin;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.StringUtil;
import netty.Chat.message.ChooseMenuRequestMessage;
import netty.Chat.message.GroupCreateRequestMessage;
import netty.Chat.message.GroupJoinRequestMessage;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/30 07:12
 * @Description:加入群聊
 */
public class GroupJoinClientOutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void write(ChannelHandlerContext ctx, Object message, ChannelPromise promise) throws Exception {
        if (!(message instanceof GroupJoinRequestMessage)) {
            super.write(ctx, message, promise);
            return;
        }
        GroupJoinRequestMessage joinMessage = (GroupJoinRequestMessage) message;
        String userName = joinMessage.getUsername();
        Scanner scanner = new Scanner(System.in);
        new Thread(()->{
           while (true){
               System.out.println("==================================");
               System.out.println("join [group name]");
               System.out.println("qm 退出当前菜单目录");
               System.out.println("==================================");
               String line = scanner.nextLine();
               if(StringUtil.isNullOrEmpty(line)){
                   continue;
               }
               String[] s = line.split(" ");
               if ("join".equals(s[0])) {
                   String groupName = s[1];
                   joinMessage.setGroupName(groupName);
                   ctx.writeAndFlush(joinMessage);
               }
               if("qm".equals(s[0])){
                   ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(userName));
                   break;
               }
           }
        },"group join").start();
    }


    //    @Override
//    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
//        if(!(msg instanceof GroupCreateRequestMessage)){
//            super.write(ctx, msg, promise);
//            return;
//        }
//        GroupCreateRequestMessage message = (GroupCreateRequestMessage) msg;
//        String from = message.getFrom();
//        Scanner scanner = new Scanner(System.in);
//        new Thread(()->{
//            while (true){
//                System.out.println(Thread.currentThread().getId()+Thread.currentThread().getName());
//                System.out.println("==================================");
//                System.out.println("gcreate [group name] [m1,m2,m3...]");
//                System.out.println("qm 退出当前菜单目录");
//                System.out.println("==================================");
//                String line = scanner.nextLine();
//                String[] s = line.split(" ");
//                if("gcreate".equals(s[0])){
//                    message.setGroupName(s[1]);
//                    HashSet<String> set = new HashSet<>(Arrays.asList(s[2]));
//                    message.setMembers(set);
//                    ctx.writeAndFlush(message);
//                }
//                //退出当前菜单
//                if("qm".equals(s[0])){
//                    ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(from));
//                    break;
//                }
//            }
//        },"GroupCreate").start();
//    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
