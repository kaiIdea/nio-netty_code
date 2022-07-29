package netty.Chat.client;

import io.netty.channel.*;
import netty.Chat.message.ChatRequestMessage;
import netty.Chat.message.ChooseMenuRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 14:53
 * @Description: 单聊消息通过控制台输入,输出给对方控制台
 *
 * 当然,消息经由服务器,转发给对方client控制台
 * 通过出站发送--服务器端入站接收,然后发送给对方
 */
@ChannelHandler.Sharable
public class ChatClientOutHandler extends ChannelOutboundHandlerAdapter {

    public static Logger log = LoggerFactory.getLogger(ChatClientOutHandler.class);

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(!(msg instanceof ChatRequestMessage)){
            return;
        }
        log.info("ChatRequestMessage {}",msg);
        ChatRequestMessage message = (ChatRequestMessage) msg;
        new Thread(()->{
            while (true){
                System.out.println("==================================");
                System.out.println("send [username] [content]");
                System.out.println("qm 退出当前菜单目录");
                System.out.println("==================================");
                Scanner scanner = new Scanner(System.in);
                String text = scanner.nextLine();
                String[] strs = text.split(" ");
                if("send".equals(strs[0])){
                    message.setTo(strs[1]);
                    message.setContent(strs[2]);
                    ctx.writeAndFlush(message);
                }
                //退出当前菜单页
                if("qm".equals(strs[0])){
                    //从出站tail结点,开始重新执行
                    ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(message.getFrom()));
                    //让这个异步线程结束执行
                    break;
                }
            }
        },"chat input").start();
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("【ChatClientOutHandler.flush】");
        super.flush(ctx);
    }
}
