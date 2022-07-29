package netty.ByteBuf;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

/**
 * @Auther: YangKai
 * @Date: 2022/7/26 10:15
 * @Description:
 */
public class TestByteBuf {

    public static void main(String[] args) {
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        ByteBufLog.info(buf);
        buf.writeBytes(new byte[]{1,2,3,4});
        buf.writeInt(5);
        buf.writeInt(6);
        ByteBufLog.info(buf);

        System.out.println(buf.readByte());
        System.out.println(buf.readByte());
        System.out.println(buf.readByte());
        System.out.println(buf.readByte());

        ByteBufLog.info(buf);
    }
}
