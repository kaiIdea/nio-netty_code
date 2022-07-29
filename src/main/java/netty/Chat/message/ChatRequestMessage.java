package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:43
 * @Description:
 */
public class ChatRequestMessage extends Message{

    private String content;
    private String from;
    private String to;

    public ChatRequestMessage() {
    }

    public ChatRequestMessage(String from) {
        this.from = from;
    }

    public ChatRequestMessage(String from, String to, String content) {
        this.content = content;
        this.from = from;
        this.to = to;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public int getMessageType() {
        return ChatRequestMessage;
    }

    @Override
    public String toString() {
        return "ChatRequestMessage{" +
                "content='" + content + '\'' +
                ", from='" + from + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
