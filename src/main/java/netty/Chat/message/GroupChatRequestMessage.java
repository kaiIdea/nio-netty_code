package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:44
 * @Description:
 */
public class GroupChatRequestMessage extends Message {
    private String content;
    private String groupName;
    private String from;

    public GroupChatRequestMessage(String from) {
        this.from = from;
    }

    public GroupChatRequestMessage(String content, String groupName) {
        this.content = content;
        this.groupName = groupName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public GroupChatRequestMessage(String from, String groupName, String content) {
        this.content = content;
        this.groupName = groupName;
        this.from = from;
    }

    @Override
    public String toString() {
        return "GroupChatRequestMessage{" +
                "content='" + content + '\'' +
                ", groupName='" + groupName + '\'' +
                ", from='" + from + '\'' +
                '}';
    }

    @Override
    public int getMessageType() {
        return GroupChatRequestMessage;
    }
}
