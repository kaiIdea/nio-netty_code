package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Auther: YangKai
 * @Date: 2022/7/24 14:45
 * @Description:
 */
public class NettyServer {

    private static String SERVER_LOG = "[server:]";

    public static void main(String[] args) throws InterruptedException {
        //创建boss事件循环线程组，只是处理连接请求
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //创建work事件循环线程组，负责负责和客户端，read and write请求
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,128)
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyServerHandler());
                            ch.pipeline().addLast(new NettyServerHandler1());
                        }
                    });

            System.out.println(SERVER_LOG+"服务已启动");
            ChannelFuture future = serverBootstrap.bind(9888).sync();
            future.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
        }
    }
}
