package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:43
 * @Description:
 */
public class GroupJoinRequestMessage extends Message {
    private String groupName;

    private String username;

    public GroupJoinRequestMessage(String username, String groupName) {
        this.groupName = groupName;
        this.username = username;
    }

    @Override
    public int getMessageType() {
        return GroupJoinRequestMessage;
    }
}
