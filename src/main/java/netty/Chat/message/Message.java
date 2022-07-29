package netty.Chat.message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:34
 * @Description:
 */
public abstract class Message implements Serializable {


    private static final Map<Integer,Class<?>> messageClasses = new HashMap<>();
    private int sequenceId;
    private int messageType;
    static {

    }
    public static Class<?> getMessageClass(int messageType){
        return messageClasses.get(messageType);
    }

    public abstract int getMessageType();

    public static final int LoginRequestMessage = 0;
    public static final int LoginResponseMessage = 1;

    public static final int ChatRequestMessage = 2;
    public static final int ChatResponseMessage = 3;

    public static final int GroupCreateRequestMessage = 4;
    public static final int GroupCreateResponseMessage = 5;

    public static final int GroupJoinRequestMessage = 6;
    public static final int GroupJoinResponseMessage = 7;

    public static final int GroupQuitRequestMessage = 8;
    public static final int GroupQuitResponseMessage = 9;

    public static final int GroupChatRequestMessage = 10;
    public static final int GroupChatResponseMessage = 11;

    public static final int GroupMembersRequestMessage = 12;
    public static final int GroupMembersResponseMessage = 13;

    public int getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(int sequenceId) {
        this.sequenceId = sequenceId;
    }
}
