package netty.ByteBuf;

import io.netty.buffer.ByteBuf;

import static io.netty.buffer.ByteBufUtil.appendPrettyHexDump;
import static io.netty.util.internal.StringUtil.NEWLINE;

/**
 * @Auther: YangKai
 * @Date: 2022/7/26 10:10
 * @Description:
 */
public class ByteBufLog {

    public static void info(ByteBuf buffer){
        int length = buffer.readableBytes();
        int rows = length/16 + (length % 15 == 0 ?0:1)+4;
        StringBuilder buf = new StringBuilder(rows*8*2)
                .append(" read_index:").append(buffer.readerIndex())
                .append(" write_index:").append(buffer.writerIndex())
                .append(" capacity:").append(buffer.capacity())
                .append(NEWLINE);
        appendPrettyHexDump(buf,buffer);
        System.out.println(buf.toString());
    }
}
