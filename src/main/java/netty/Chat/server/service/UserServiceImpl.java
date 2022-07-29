package netty.Chat.server.service;

import io.netty.util.internal.StringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: YangKai
 * @Date: 2022/7/28 19:59
 * @Description:
 */
public class UserServiceImpl implements UserService{

    private Map<String,String> userMap = new ConcurrentHashMap<>();

    {
        userMap.put("zhangsan","123");
        userMap.put("lisi","123");
        userMap.put("wangwu","123");
        userMap.put("zhaoliu","123");
        userMap.put("boy","123");
    }

    @Override
    public boolean login(String userName, String password) {
        String pass = userMap.get(userName);
        if(StringUtil.isNullOrEmpty(pass)){
            return false;
        }
        return pass.equals(password);
    }
}
