package netty.Chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import netty.Chat.client.handler.chat.ChatClientInHandler;
import netty.Chat.client.handler.chat.ChatClientOutHandler;
import netty.Chat.client.handler.groupChat.GroupChatClienInHandler;
import netty.Chat.client.handler.groupChat.GroupChatClienOutHandler;
import netty.Chat.client.handler.groupCreate.GroupCreateClienInHandler;
import netty.Chat.client.handler.groupCreate.GroupCreateClienOutHandler;
import netty.Chat.client.handler.groupJoin.GroupJoinClientInHandler;
import netty.Chat.client.handler.groupJoin.GroupJoinClientOutHandler;
import netty.Chat.client.handler.groupMembers.GroupMembersClientInHandler;
import netty.Chat.client.handler.groupMembers.GroupMembersClientOutHandler;
import netty.Chat.client.handler.groupQuit.GroupQuitClientInHandler;
import netty.Chat.client.handler.groupQuit.GroupQuitClientOutHandler;
import netty.Chat.client.handler.login.LoginClientInHandler;
import netty.Chat.protocol.MessageCodecShare;
import netty.Chat.protocol.ProcotolFrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 19:23
 * @Description:
 */
public class ChatClient {
    public static Logger log = LoggerFactory.getLogger(ChatClient.class);

    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup loopGroup = new NioEventLoopGroup();
        LoggingHandler loggingHandler = new LoggingHandler();
        MessageCodecShare messageCodec = new MessageCodecShare();
        try {
            ChannelFuture channelFuture = new Bootstrap().group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new ProcotolFrameDecoder());
                            //ch.pipeline().addLast(loggingHandler);
                            ch.pipeline().addLast(messageCodec);
                            ch.pipeline().addLast(new LoginClientInHandler());
                            ch.pipeline().addLast(new ChatClientInHandler());
                            ch.pipeline().addLast(new ChatClientOutHandler());
                            ch.pipeline().addLast(new ChooseMenuHandler());
                            ch.pipeline().addLast(new GroupCreateClienInHandler());
                            ch.pipeline().addLast(new GroupCreateClienOutHandler());
                            ch.pipeline().addLast(new GroupChatClienInHandler());
                            ch.pipeline().addLast(new GroupChatClienOutHandler());
                            ch.pipeline().addLast(new GroupQuitClientInHandler());
                            ch.pipeline().addLast(new GroupQuitClientOutHandler());
                            ch.pipeline().addLast(new GroupJoinClientInHandler());
                            ch.pipeline().addLast(new GroupJoinClientOutHandler());
                            ch.pipeline().addLast(new GroupMembersClientInHandler());
                            ch.pipeline().addLast(new GroupMembersClientOutHandler());

                        }
                    })
                    .connect("127.0.0.1", 8080).sync();
            Channel channel = channelFuture.channel();
            //??????????????????????????????????????????????????????close???????????????????????????????????????
            //????????????????????????
            log.info("*******************************????????????*******************************");
            channel.closeFuture().sync();
            log.info("*******************************client????????????*******************************");
        } finally {
            log.info("*******************************?????????????????????*******************************");
            loopGroup.shutdownGracefully();
        }
    }
}
