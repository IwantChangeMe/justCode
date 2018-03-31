package justcode.com.common.util.coder;

import android.util.Log;

import java.net.URLEncoder;

/**
 * Description   :参数加密相关
 * Author        :jelly.liao
 * Time          :2017/3/6 14:55
 * Modify        :
 */
public class AndroidCoder {

    private static final String TAG = AndroidCoder.class.getSimpleName();
    private static final String cset = "UTF-8";
    private static String publicKey = "key";

    public static String encode(String msg) {
        try {
            return RSACoder.encryptBASE64(RSACoder.encryptByPublicKey(msg.getBytes(cset), publicKey));
        } catch (Exception e) {
            return null;
        }

    }

    public static String byteArrayToStr(byte[] byteArray) {
        if (byteArray == null) {
            return null;
        }
        String str = new String(byteArray);
        return str;
    }
    public static String urlEncode(String msg) {
        try {
            return URLEncoder.encode(msg);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
            return null;
        }

    }

}
