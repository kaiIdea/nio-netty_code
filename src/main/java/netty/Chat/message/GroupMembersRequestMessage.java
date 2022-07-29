package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:44
 * @Description:
 */
public class GroupMembersRequestMessage extends Message {
    private String groupName;

    public GroupMembersRequestMessage(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
