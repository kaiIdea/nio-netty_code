package netty.Chat.message;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 20:16
 * @Description:
 */
public abstract class AbstractResponseMessage extends Message {

    private boolean success;
    private String result;

    private String data;

    public AbstractResponseMessage() {
    }

    public AbstractResponseMessage(boolean success, String result) {
        this.success = success;
        this.result = result;
    }

    public AbstractResponseMessage(boolean success, String result, String data) {
        this.success = success;
        this.result = result;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AbstractResponseMessage{" +
                "success=" + success +
                ", result='" + result + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
