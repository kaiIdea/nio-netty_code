package netty.Chat.message;

import java.util.Set;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:43
 * @Description:
 */
public class GroupCreateRequestMessage extends Message {
    private String groupName;
    private Set<String> members;

    public GroupCreateRequestMessage(String groupName, Set<String> members) {
        this.groupName = groupName;
        this.members = members;
    }

    @Override
    public int getMessageType() {
        return GroupCreateRequestMessage;
    }
}
