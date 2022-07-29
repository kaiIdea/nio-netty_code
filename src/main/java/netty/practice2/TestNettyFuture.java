package netty.practice2;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Auther: YangKai
 * @Date: 2022/7/26 07:28
 * @Description:
 */
public class TestNettyFuture {

    public static Logger log = LoggerFactory.getLogger(TestNettyFuture.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //获取事件循环组
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        log.info("init thread pool group success.");
        //获取
        EventLoop eventLoop = eventLoopGroup.next();
        log.info("get thread success.");
        Future<Integer> future = eventLoop.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                log.info("执行计算");
                return 888;
            }
        });
        log.info("wait result...");
//        log.info("wait get resultNow...");
//        Integer now = future.getNow();
//        log.info("resultNow is "+now);
//
//        log.info("wait result...");
//        Integer result = future.get();
//        log.info("result is :"+result);


        future.addListener(new GenericFutureListener<Future<? super Integer>>() {
            @Override
            public void operationComplete(Future<? super Integer> future) throws Exception {
                log.info("result is :"+future.get());
            }
        });

        log.info("wait close thread pool group...");
        eventLoopGroup.shutdownGracefully();
        log.info("thread pool group close success.");
    }
}
