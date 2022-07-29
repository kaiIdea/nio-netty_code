package netty.protocl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 14:12
 * @Description:
 */
public class TestLengthFileDecoder {

    public static void main(String[] args) {


        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(
                        1024,0,4,1,5),
                new LoggingHandler()
        );
        ByteBuf buffers = ByteBufAllocator.DEFAULT.buffer();
        extracted(buffers,"hello, world");
        extracted(buffers,"hi");
        extracted(buffers,"how are you.");
        extracted(buffers,"I'm fine.");
        extracted(buffers,"niece to meet you.");
        channel.writeInbound(buffers);
    }

    private static void extracted(ByteBuf buffers,String message) {
        byte[] bytes = message.getBytes();
        int length = bytes.length;
        buffers.writeInt(length);
        buffers.writeByte(1);
        buffers.writeBytes(bytes);
    }
}
