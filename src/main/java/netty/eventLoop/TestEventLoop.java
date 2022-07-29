package netty.eventLoop;

import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Auther: YangKai
 * @Date: 2022/7/25 10:29
 * @Description:
 */
public class TestEventLoop {

    public static Logger log = LoggerFactory.getLogger(TestEventLoop.class);

    public static void main(String[] args) {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

//        System.out.println(eventLoopGroup.next());
//        System.out.println(eventLoopGroup.next());
//        System.out.println(eventLoopGroup.next());
//        System.out.println(eventLoopGroup.next());
//        System.out.println(eventLoopGroup.next());

        eventLoopGroup.next().execute(()->{
            try {
                Thread.sleep(3);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            log.info("执行任务.");
        });
        log.info("main");
//        eventLoopGroup.next().execute(()->{
//            try {
//                Thread.sleep(2);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(Thread.currentThread().getName()+":在执行任务.");
//        });
//        eventLoopGroup.next().execute(()->{
//            try {
//                Thread.sleep(1);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//            System.out.println(Thread.currentThread().getName()+":在执行任务.");
//        });
//        eventLoopGroup.next().scheduleAtFixedRate(()->{
//            //System.out.println(Thread.currentThread().getName()+":在执行任务.");
//            log.info(":在执行任务.");
//        },0,2, TimeUnit.SECONDS);
    }
}
