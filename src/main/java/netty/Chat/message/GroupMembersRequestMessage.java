package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:44
 * @Description:
 */
public class GroupMembersRequestMessage extends Message {

    private String userName;
    private String groupName;

    public GroupMembersRequestMessage() {
    }



    public GroupMembersRequestMessage(String userName) {
        this.userName = userName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public int getMessageType() {
        return GroupMembersRequestMessage;
    }
}
