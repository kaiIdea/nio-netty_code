package netty.practice1;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/25 17:07
 * @Description:
 */

public class NettyServerHandler1 extends ChannelInboundHandlerAdapter {

    public static Logger log = LoggerFactory.getLogger(NettyServerHandler1.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        System.out.println("111111111");
        log.info("handler1:已接受到数据，正在加工{}",buf.toString(CharsetUtil.UTF_8));
        ctx.fireChannelRead(buf);
    }
}
