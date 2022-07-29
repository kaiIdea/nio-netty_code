package netty.practice2;

import netty.practice1.NettyClient1;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @Auther: YangKai
 * @Date: 2022/7/26 07:19
 * @Description:
 */
public class TestJdkFuture {

    public static Logger log = LoggerFactory.getLogger(TestJdkFuture.class);

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(2);
        Future<Integer> future = pool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1);
                log.info("进行计算");
                return 888;
            }
        });
        log.info("wait result...");
        Integer result = future.get();
        log.info("返回结果："+result);
    }
}
