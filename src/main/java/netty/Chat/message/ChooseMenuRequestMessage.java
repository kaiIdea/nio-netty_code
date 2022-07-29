package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/29 19:08
 * @Description:
 */
public class ChooseMenuRequestMessage extends Message {

    private String userName;
    private String choosMenuName;

    public ChooseMenuRequestMessage() {
    }

    public ChooseMenuRequestMessage(String userName, String choosMenuName) {
        this.userName = userName;
        this.choosMenuName = choosMenuName;
    }

    public ChooseMenuRequestMessage(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getChoosMenuName() {
        return choosMenuName;
    }

    public void setChoosMenuName(String choosMenuName) {
        this.choosMenuName = choosMenuName;
    }

    @Override
    public int getMessageType() {
        return 888;
    }
}
