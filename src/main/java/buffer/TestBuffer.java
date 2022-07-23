package buffer;

import java.nio.Buffer;
import java.nio.ByteBuffer;

/**
 * @Auther: YangKai
 * @Date: 2022/7/20 06:39
 * @Description: nio缓冲区操作类
 */
public class TestBuffer {


    public static void main(String[] args) {

        String str = "abcd";
        //在堆内存中创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        System.out.println("-----------------------new ()-----------------------");
        System.out.println("mark:"+buffer.mark());
        System.out.println("position:"+buffer.position());
        System.out.println("limit:"+buffer.limit());
        System.out.println("capacity:"+buffer.capacity());
        //将数据放入缓冲区
        buffer.put(str.getBytes());
        System.out.println("-----------------------put ()-----------------------");
        System.out.println("mark:"+buffer.mark());
        System.out.println("position:"+buffer.position());
        System.out.println("limit:"+buffer.limit());
        System.out.println("capacity:"+buffer.capacity());
        //切换模式，将buffer的写模式，切换为读模式
        //这个切换，其实就是将下标进行重置，让其在数组的首位
        buffer.flip();
        System.out.println("-----------------------flip ()-----------------------");
        System.out.println("mark:"+buffer.mark());
        System.out.println("position:"+buffer.position());
        System.out.println("limit:"+buffer.limit());
        System.out.println("capacity:"+buffer.capacity());
        System.out.println("-----------------------get ()-----------------------");
        System.out.println("mark:"+buffer.mark());
        System.out.println("Value:"+new String(new byte[]{buffer.get(), buffer.get(),buffer.get()}));
        System.out.println("position:"+buffer.position());
        System.out.println("limit:"+buffer.limit());
        System.out.println("capacity:"+buffer.capacity());
        //重置数组下标至，mark标记的位置
        buffer.rewind();
        System.out.println("Value1:"+new String(new byte[]{buffer.get(), buffer.get(),buffer.get()}));


        Buffer buffer1 = ByteBuffer.allocateDirect(1024);


    }
}
