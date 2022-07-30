package netty.Chat.client.handler.login;

import io.netty.channel.*;
import netty.Chat.message.ChatRequestMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 10:56
 * @Description:
 */
@ChannelHandler.Sharable
public class LoginClientOutHandler extends ChannelOutboundHandlerAdapter {

    public static Logger log = LoggerFactory.getLogger(LoginClientOutHandler.class);

    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        log.info("【LoginClientOutHandler.read】");
        ChatRequestMessage msg = new ChatRequestMessage();
        log.info("LoginClientOutHandler.read,{}",msg);
        super.read(ctx);
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        log.info("【LoginClientOutHandler.write】{}",msg);
        Scanner scanner = new Scanner(System.in);
        Channel channel = ctx.channel();
        while (true){
            System.out.println("==================================");
            System.out.println("send [username] [content]");
            System.out.println("gsend [group name] [content]");
            System.out.println("gcreate [group name] [m1,m2,m3...]");
            System.out.println("gmembers [group name]");
            System.out.println("gjoin [group name]");
            System.out.println("gquit [group name]");
            System.out.println("quit");
            System.out.println("==================================");
            String command = scanner.nextLine();
            String[] strArray = command.split(" ");
            switch (strArray[0]){
                case "send":
                    //发生消息给指定人
                    //channel.writeAndFlush()
                    ctx.write(msg, promise);
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
        }

    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        log.info("【LoginClientOutHandler.flush】");
        super.flush(ctx);
    }
}
