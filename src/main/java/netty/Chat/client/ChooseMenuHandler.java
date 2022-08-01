package netty.Chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.util.internal.StringUtil;
import netty.Chat.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 19:07
 * @Description: client用户选择菜单处理器
 */
public class ChooseMenuHandler extends ChannelOutboundHandlerAdapter {

    public static Logger log = LoggerFactory.getLogger(ChooseMenuHandler.class);

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
            log.info("*********主菜单-"+Thread.currentThread().getId()+Thread.currentThread().getName());
            //登录成功,选择菜单，发送消息
            System.out.println("==================================");
            System.out.println("send 单聊");
            System.out.println("gsend 群聊");
            System.out.println("gcreate 发起群聊");
            System.out.println("gmembers 查询群聊");
            System.out.println("join 加入群聊");
            System.out.println("gquit 退出群聊");
            System.out.println("quit 退出");
            System.out.println("==================================");
            String command = scanner.nextLine();
            if(!StringUtil.isNullOrEmpty(command)){
                dispatchRequest(command,ctx,userName);
            }
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
                message = new GroupChatRequestMessage(userName);
                break;
            case "gcreate":
                message = new GroupCreateRequestMessage(userName);
                break;
            case "gmembers":
                message = new GroupMembersRequestMessage(userName);
                break;
            case "join":
                message = new GroupJoinRequestMessage(userName);
                break;
            case "gquit":
                message = new GroupQuitRequestMessage(userName);
                break;
            case "quit":
                break;
            default:
        }
        if (null != message) {
            //通过channel调用writeAndFlush出站，开始从tail结点依次执行
            ctx.channel().writeAndFlush(message);
        }
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        super.flush(ctx);
    }
}
