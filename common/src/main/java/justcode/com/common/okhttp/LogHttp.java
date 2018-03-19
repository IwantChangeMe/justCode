package justcode.com.common.okhttp;


import android.text.TextUtils;
import android.util.Log;

public class LogHttp {

    public static void log(String msg) {
        if (TextUtils.isEmpty(msg))
            msg = "没有获取到日志信息";
        Log.d("LogHttp", msg);
    }
}
