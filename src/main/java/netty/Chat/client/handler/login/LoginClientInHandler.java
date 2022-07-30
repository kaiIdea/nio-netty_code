package netty.Chat.client.handler.login;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.Chat.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 06:54
 * @Description:登录处理器
 */
@ChannelHandler.Sharable
public class LoginClientInHandler extends ChannelInboundHandlerAdapter {

    public static Logger log = LoggerFactory.getLogger(LoginClientInHandler.class);

    /**
     * 跟服务端连接建立完成以后,会调用这个方法
     *  client用户输入账号密码,然后发送给服务端进行验证
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        CountDownLatch latch = new CountDownLatch(1);
        LoginRequestMessage loginMessage = new LoginRequestMessage();
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入用户名:");
            String userName = scanner.next();
            System.out.print("请输入密码:");
            String password = scanner.next();
            loginMessage.setUserName(userName);
            loginMessage.setPassword(password);
            latch.countDown();
        }, "login input").start();
        latch.await();
        ctx.writeAndFlush(loginMessage);
    }

    /**
     * 服务端验证完成以后,返回client登录结果
     * 如果登录失败,关闭client通道
     * 如果成功,通过writeAndFlush,跳转到tail结点指向的ChooseMenuHandler处理器
     * ChooseMenuHandler这个处理器可以展示菜单,进行下一步
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("client msg:{}", msg);
        if (!(msg instanceof LoginResponseMessage)) {
            super.channelRead(ctx,msg);
            return;
        }
        LoginResponseMessage response = (LoginResponseMessage) msg;
        if (!response.isSuccess()) {
            //登录失败
            ctx.channel().close();
            return;
        }
        String userName = response.getData();
        //通过writeAndFlush，开始执行出战，出站时，从tail结点开始执行.
        //预计首次执行选择菜单执行器
        ctx.channel().writeAndFlush(new ChooseMenuRequestMessage(userName));
    }

}
