package netty.Chat.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import netty.Chat.message.LoginRequestMessage;
import netty.Chat.message.LoginResponseMessage;
import netty.Chat.server.service.UserServiceFactory;
import netty.Chat.server.session.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 07:25
 * @Description:
 */
public class LoginServerHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    public static Logger log = LoggerFactory.getLogger(LoginServerHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage message) throws Exception {
        log.info("LoginServerHandler接收到登录信息:{}",message);
        String userName = message.getUserName();
        String password = message.getPassword();
        LoginResponseMessage response = new LoginResponseMessage(userName);
        boolean loginResult = UserServiceFactory.getUserService().login(userName, password);
        if(loginResult){
            response = new LoginResponseMessage(loginResult,userName+" 登录成功",userName);
            SessionFactory.getSession().bind(ctx.channel(),userName);
        }
        ctx.writeAndFlush(response);
    }
}
