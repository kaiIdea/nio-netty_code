package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 16:43
 * @Description:
 */
public class ChatResponseMessage extends AbstractResponseMessage{

    private String from;
    private String content;

    public ChatResponseMessage(String from, String content) {
        super(true,null);
        this.from = from;
        this.content = content;
    }

    public ChatResponseMessage(boolean success, String result,String from) {
        super(success, result);
        this.from = from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public int getMessageType() {
        return ChatResponseMessage;
    }

    @Override
    public String toString() {
        return "ChatResponseMessage{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                "} " + super.toString();
    }
}
