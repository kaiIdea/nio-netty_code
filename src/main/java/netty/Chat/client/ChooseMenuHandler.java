package netty.Chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import netty.Chat.message.ChatRequestMessage;
import netty.Chat.message.ChooseMenuRequestMessage;
import netty.Chat.message.Message;

import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 19:07
 * @Description: client用户选择菜单处理器
 */
public class ChooseMenuHandler extends ChannelOutboundHandlerAdapter {


    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if (!(msg instanceof ChooseMenuRequestMessage)) {
            super.write(ctx, msg, promise);
            return;
        }
        ChooseMenuRequestMessage choose = (ChooseMenuRequestMessage) msg;
        String userName = choose.getUserName();
        new Thread(()->{
            Scanner scanner = new Scanner(System.in);
            //登录成功,选择菜单，发送消息
            System.out.println("==================================");
            System.out.println("send 单聊");
            System.out.println("gsend 群聊");
            System.out.println("gcreate 发起群聊");
            System.out.println("gmembers 查询群聊");
            System.out.println("gjoin 加入群聊");
            System.out.println("gquit 退出群聊");
            System.out.println("quit 退出");
            System.out.println("==================================");
            String command = scanner.nextLine();
            dispatchRequest(command,ctx,userName);
        },"choose menu").start();
    }

    //跳转到对应聊天出站write,然后进行消息处理出战
    public void dispatchRequest(String send, ChannelHandlerContext ctx,String userName) {
        Message message = null;
        switch (send) {
            case "send":
                //发生消息给指定人
                message = new ChatRequestMessage(userName);
                break;
            case "gsend":
                break;
            case "gcreate":
                break;
            case "gmembers":
                break;
            case "gjoin":
                break;
            case "gquit":
                break;
            case "quit":
                break;
            default:
        }
        ctx.writeAndFlush(message);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
