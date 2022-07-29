package nio.charSet;

import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;

/**
 * @Auther: YangKai
 * @Date: 2022/7/21 07:08
 * @Description:
 */
public class CharSetTest {


    public static Charset get(){
        return Charset.forName("UTF-8");
    }

    public static CharsetEncoder getEncoder(){
        return get().newEncoder();
    }


    public static CharsetDecoder getDecoder(){
        return get().newDecoder();
    }
}
