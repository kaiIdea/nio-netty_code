package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 17:04
 * @Description:
 */
public class LoginResponseMessage extends AbstractResponseMessage {

    public LoginResponseMessage(boolean success, String reason) {
        super(success, reason);
    }

    public LoginResponseMessage(boolean success, String result, String data) {
        super(success, result, data);
    }

    public LoginResponseMessage(String reason) {
        super(false, reason+":登录失败,用户名或密码不正确",reason);
    }

    @Override
    public int getMessageType() {
        return LoginResponseMessage;
    }
}
