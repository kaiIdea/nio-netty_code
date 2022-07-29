package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:43
 * @Description:
 */
public class GroupCreateResponseMessage extends AbstractResponseMessage {

    public GroupCreateResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    @Override
    public int getMessageType() {
        return GroupCreateResponseMessage;
    }
}
