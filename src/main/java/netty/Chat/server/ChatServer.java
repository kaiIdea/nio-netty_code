package netty.Chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import netty.Chat.protocol.MessageCodecShare;
import netty.Chat.protocol.ProcotolFrameDecoder;
import netty.Chat.server.handler.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 19:23
 * @Description:
 */
public class ChatServer {

    public static Logger log = LoggerFactory.getLogger(ChatServer.class);

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup boos = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecShare messageCodec = new MessageCodecShare();
        try {
            ChannelFuture channelFuture = new ServerBootstrap()
                    .group(boos, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            //字节数据解析器,
                            ch.pipeline().addLast(new ProcotolFrameDecoder());
                            ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(new LoginServerHandler());
                            ch.pipeline().addLast(new ChatServerHandler());
                            ch.pipeline().addLast(new GroupCreateServerHandler());
                            ch.pipeline().addLast(new GroupChatServerHandler());
                            ch.pipeline().addLast(new GroupQuitServerHandler());
                            ch.pipeline().addLast(new GroupJoinServerHandler());
                            ch.pipeline().addLast(new GroupMembersServerHandler());
                        }
                    })
                    .bind(8080).sync();
            Channel channel = channelFuture.channel();
            channel.closeFuture().sync();
        } finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
