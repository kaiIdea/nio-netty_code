package netty.Chat.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 20:11
 * @Description:
 */
public class ProcotolFrameDecoder extends LengthFieldBasedFrameDecoder {

    public ProcotolFrameDecoder() {
        this(1024,12,4,0,0);
    }

    public ProcotolFrameDecoder(int maxFrameLength, int lengthFieldOffset, int lengthFieldLength, int lengthAdjustment, int initialBytesToStrip) {
        super(maxFrameLength, lengthFieldOffset, lengthFieldLength, lengthAdjustment, initialBytesToStrip);
    }
}
