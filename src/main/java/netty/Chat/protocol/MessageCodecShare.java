package netty.Chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import netty.Chat.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 09:06
 * @Description:
 */
@ChannelHandler.Sharable
public class MessageCodecShare extends MessageToMessageCodec<ByteBuf, Message> {

    public static Logger log = LoggerFactory.getLogger(MessageCodecShare.class);


    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, List<Object> list) throws Exception {
        ByteBuf out = ctx.alloc().buffer();;
        // 1. 4字节的魔数
        out.writeBytes("dudu".getBytes());
        //2. 1字节的协议版本号
        out.writeByte(1);
        //3. 1字节的序列化方式,0:jdk,1:json
        out.writeByte(0);
        //4.业务指令类型
        out.writeByte(message.getMessageType());
        //5.请求序号,唯一序列号,实现异步
        out.writeInt(message.getSequenceId());
        //无实际意义,对齐填充
        out.writeByte(0);
        byte[] bytes = objectStream(message);
        int length = bytes.length;
        //6.正文长度
        out.writeInt(length);
        //7.消息正文
        out.writeBytes(bytes);

        list.add(out);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBuf buf = in.readBytes(4);
        //魔数
        String magic = new String(ByteBufUtil.getBytes(buf));
        int version = in.readByte();
        int serializableType = in.readByte();
        int messageType = in.readByte();
        int sequenceId = in.readInt();
        in.readByte();
        int length = in.readInt();

        byte[] bytes = new byte[length];
        in.readBytes(bytes,0,length);
        ObjectInputStream os = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Message message = (Message) os.readObject();
        out.add(message);
        log.info("{}, {;}, {}, {}, {}, {}, {}",magic,version,serializableType,messageType,
                sequenceId,length);
        log.info("message: {}",message);
    }

    private static byte[] objectStream(Message message) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteArray);
        stream.writeObject(message);
        return byteArray.toByteArray();
    }
}
