package justcode.com.common;


import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.tencent.bugly.crashreport.CrashReport;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import justcode.com.common.okhttp.MycookieJar;
import justcode.com.common.okhttp.SSLSocketClient;
import okhttp3.OkHttpClient;

public class BaseApplication extends Application {

   public static OkHttpClient client;

    @Override
    public void onCreate() {
        super.onCreate();
        //配置bugly
        initBugly();
        //配置okhttp
        initOKHTTP();
    }

    private void initOKHTTP() {
        client = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置  这句话，忽略https证书验证
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置  这句话，忽略host验证
                .cookieJar(new MycookieJar())
                .build();
    }

    private void initBugly() {
        Context context = getApplicationContext();
        // 获取当前包名
        String packageName = context.getPackageName();
        // 获取当前进程名
        String processName = getProcessName(android.os.Process.myPid());
        // 设置是否为上报进程
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setUploadProcess(processName == null || processName.equals(packageName));
        /**
         * 建议在测试阶段建议设置成true, 输出详细的Bugly SDK的Log, 每一条Crash都会被立即上报, 自定义日志将会在Logcat中输出
         * 发布时设置为false
         */
        CrashReport.initCrashReport(context, "c8a265acbb", true, strategy);
    }

    /**
     * 获取进程号对应的进程名
     *
     * @param pid 进程号
     * @return 进程名
     */
    private static String getProcessName(int pid) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("/proc/" + pid + "/cmdline"));
            String processName = reader.readLine();
            if (!TextUtils.isEmpty(processName)) {
                processName = processName.trim();
            }
            return processName;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
        return null;
    }
}
