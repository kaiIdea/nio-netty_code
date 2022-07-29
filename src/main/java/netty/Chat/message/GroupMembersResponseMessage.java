package netty.Chat.message;

import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:44
 * @Description:
 */
public class GroupMembersResponseMessage extends Message {

    private Set<String> members;

    public GroupMembersResponseMessage(Set<String> members) {
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupMembersResponseMessage;
    }
}
