package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 17:04
 * @Description:
 */
public class LoginRequestMessage extends Message{
    private String userName;
    private String password;

    public LoginRequestMessage() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequestMessage(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginRequestMessage{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public int getMessageType() {
        return LoginRequestMessage;
    }
}
