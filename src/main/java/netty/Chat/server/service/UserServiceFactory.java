package netty.Chat.server.service;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 20:05
 * @Description:
 */
public abstract class UserServiceFactory {


    private static UserService userService = new UserServiceImpl();

    public static UserService getUserService(){
        return userService;
    }
}
