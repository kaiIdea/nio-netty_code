package netty.practice2;

import io.netty.channel.EventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

/**
 * @Auther: YangKai
 * @Date: 2022/7/26 07:42
 * @Description: Promise为结果容器
 */
public class TestNettyPromise {

    public static Logger log = LoggerFactory.getLogger(TestNettyFuture.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        EventLoop eventLoop = new NioEventLoopGroup().next();
        DefaultPromise<Integer> defaultPromise = new DefaultPromise<>(eventLoop);
        new Thread(()->{
            log.info("执行计算");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            defaultPromise.setSuccess(888);
        }).start();
        log.info("wait result...");
        //get 也是同步阻塞的。
        log.info("result is :"+defaultPromise.get());
        log.info("invoke success.");
    }
}
