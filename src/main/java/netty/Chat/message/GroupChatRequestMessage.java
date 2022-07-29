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
