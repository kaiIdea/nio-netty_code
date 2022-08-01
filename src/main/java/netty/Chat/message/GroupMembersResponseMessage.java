package netty.Chat.message;

import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:44
 * @Description:
 */
public class GroupMembersResponseMessage extends AbstractResponseMessage {

    private Set<String> members;

    public GroupMembersResponseMessage() {
    }

    public GroupMembersResponseMessage(Set<String> members) {
        this.members = members;
    }

    public GroupMembersResponseMessage(boolean success, String result) {
        super(success, result);
    }

    public GroupMembersResponseMessage(boolean success,Set<String> members) {
        super(success, null);
        this.members = members;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupMembersResponseMessage;
    }
}
