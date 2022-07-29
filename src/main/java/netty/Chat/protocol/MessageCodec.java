package netty.Chat.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import netty.Chat.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.List;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:46
 * @Description:通过继承双工的ByteToMessageCodec类,
 * 可以实现消息入站的时候进行解码,消息出站的时候进行编码
 *
 * 这块要特别注意,解码处理,是在粘包/半包 处理之后进行的,所以目前这块不考虑处理粘包/半包问题
 *
 *
 *
 * 此数据解析器,由于没有在类上标注shareable注解. 造成这个解析器,不能在多个通道之间共享.
 * 也就是说,只能在一个Channel中使用,防止线程不安全.
 * 也不能在这个类上直接加shareable注解.
 * ByteToMessageCodec构造方法里面明确要求,子类不能加shareable注解
 * 牵扯到数据解析半包问题,或者类里面的数据状态被并发修改
 * 所以这个类废弃，构造一个新的可以多个channel共享的解析器
 * @code MessageCodecShare
 */
public class MessageCodec extends ByteToMessageCodec<Message> {


    public static Logger log = LoggerFactory.getLogger(MessageCodec.class);
    /**
     *
     * @param ctx
     * @param message
     * @param out ByteBuf已经被Netty创建好,只需要将msg编码完成后,进行写入
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Message message, ByteBuf out) throws Exception {
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
    }

    private static byte[] objectStream(Message message) throws IOException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(byteArray);
        stream.writeObject(message);
        return byteArray.toByteArray();
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


}
