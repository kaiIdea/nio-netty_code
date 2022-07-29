package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * @Auther: YangKai
 * @Date: 2022/7/24 21:15
 * @Description:
 */
public class NettyServerHandler1 extends ChannelInboundHandlerAdapter {

    private static String SERVER_LOG = "[server1:]";
    /**
     * 读取客户端发送得消息
     * @param ctx
     * @param msg 客户端发送消息
     *
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println(SERVER_LOG+buf.toString(CharsetUtil.UTF_8));
        System.out.println(SERVER_LOG+"客户端地址："+ctx.channel().remoteAddress());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer(SERVER_LOG+"hello,客户端~",CharsetUtil.UTF_8 ));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
