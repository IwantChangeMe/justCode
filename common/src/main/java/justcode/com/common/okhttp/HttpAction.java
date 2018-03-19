package justcode.com.common.okhttp;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import justcode.com.common.BaseApplication;
import justcode.com.common.okhttp.request.GetRequest;
import justcode.com.common.okhttp.request.PostRequest;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class HttpAction<T> extends InterceptorHttpAction<T, String> {

    //每个接口都需要传递的公共参数
    private static final String OS_VERSION = "osVersion";
    private static final String APP_VERSION = "appVersion";
    private static final String PHONE_TYPE = "phoneType";
    private static final String PHONE_ID = "phoneID";
    private static final String OS_TYPE_ANDROID = "2";
    private static final String APP_KEY = "appName";
    private static final String APP_NAME = "sr";
    private static final String INPUT1 = "input1";
    private static final String INPUT2 = "input2";

    private Map<String, String> map = new HashMap<>();          //参数集合(String 参数)
    private Map<String, File> fileMap = new HashMap<>();       //参数集合(文件 参数)
    private Map<String, String> headerMap = new HashMap<>();   //参数集合，header
    private String tag;
    private int connTimeout = 0;
    private int readTimeout = 0;
    private int writeTimeout = 0;
    private boolean isPost = true;

    private static String host; // 默认父级域名，比如http://www.baidu.com/
    private String rPath;//子级域名，如 books 结合父级域名就是http://www.baidu.com/books  后面接参数

    public HttpAction(String rPath) {
        this(host, rPath);
    }

    public HttpAction(String host, String rPath) {
        HttpAction.host = host;
        this.rPath = rPath;
        //添加公共参数
        addCommonParams();
    }

    /**
     * 设置父级域名
     *
     * @param host
     */
    public static void setHost(String host) {
        HttpAction.host = host;
    }

    /**
     * 设置请求模式 get 和post  默认post
     * 如果调用此方法则本次请求为get请求
     */
    public HttpAction useGet() {
        isPost = false;
        return this;
    }

    /**
     * 为本次请求增加tag，用以区分请求
     */
    public HttpAction setTag(String tag) {
        this.tag = tag;
        return this;
    }

    /**
     * 为单次请求配置连接超时时间
     *
     * @param timeout 单位s
     */
    public HttpAction connTimeOut(int timeout) {
        this.connTimeout = timeout;
        return this;
    }

    /**
     * 为单次请求配置读取超时时间
     *
     * @param timeout 单位s
     */
    public HttpAction readTimeOut(int timeout) {
        this.readTimeout = timeout;
        return this;
    }

    /**
     * 为单次请求配置写入超时时间
     *
     * @param timeout 单位s
     */
    public HttpAction writeTimeOut(int timeout) {
        this.writeTimeout = timeout;
        return this;
    }

    /**
     * 添加普通参数
     */
    public HttpAction add(String key, String value) {
        map.put(key, value);
        return this;
    }

    /**
     * 添加文件参数
     */
    protected HttpAction addFile(String key, File file) {
        fileMap.put(key, file);
        return this;
    }

    protected HttpAction addHeader(String key, String value) {
        headerMap.put(key, value);
        return this;
    }

    @Override
    public void execute() {
        if (TextUtils.isEmpty(host)) return;
        runOnStart();   //请求开始前的回调
        final String url = host.concat(rPath);//拼接父级域名和子级域名，构成最终的url
        Request request = null;
        HashMap<String, Object> tempMap = new HashMap<>();
        tempMap.putAll(map);


        //post请求和get请求
        if (isPost) {
            request = PostRequest.Build(url, map);
            if (fileMap != null && fileMap.size() > 0) {
                tempMap.putAll(fileMap);
            }
        } else {
            request = GetRequest.build(url, map);
        }

        OkHttpClient.Builder builder = BaseApplication.builder;
        if (connTimeout > 0) builder.connectTimeout(connTimeout, TimeUnit.SECONDS);
        if (readTimeout > 0) builder.readTimeout(readTimeout, TimeUnit.SECONDS);
        if (writeTimeout > 0) builder.writeTimeout(writeTimeout, TimeUnit.SECONDS);
        LogHttp.log("请求:\n" + url.concat("\n").concat(JSON.toJSONString(tempMap)));
        addHeader(builder);
        OkHttpClient client = builder.build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //主动取消请求
                if (e != null && !TextUtils.isEmpty(e.getMessage()) && "Socket closed".equals(e.getMessage()))
                    return;

                if (!TextUtils.isEmpty(url) && e != null && !TextUtils.isEmpty(e.getMessage()))
                    LogHttp.log("响应:\n" + url.concat("\t").concat("onError").concat("\t").concat(e.getMessage()));
                //请求结束后的回调(Gson解析前)
                runOnComplet();
                //请求结束后的回调(Gson解析后)
                runOnResult(HttpResult.defaultErrorResult);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogHttp.log("响应:\n" + url.concat("\t").concat("onResponse").concat("\n").concat(response.body().string()));
                //请求结束后的回调(Gson解析前)
                runOnComplet();
                try {
                    //Gson解析响应
                    HttpResult<T> httpResult = new HttpResult<>();
                    httpResult.setEntity(decodeAndProcessModel(response.body().toString(), httpResult));
                    runOnResult(httpResult);
                } catch (Exception e) {
                    e.printStackTrace();
                    runOnResult(HttpResult.paseErrorResult);
                }
            }

        });

    }

    private void addHeader(OkHttpClient.Builder builder) {
        if (headerMap != null && headerMap.size() > 0) {
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Request.Builder builder = chain.request().newBuilder();
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        builder.addHeader(entry.getKey(), entry.getValue());
                    }

                    return chain.proceed(request);
                }
            });
        }
    }

    /**
     * 解析公共响应
     */
    private T decodeAndProcessModel(String response, HttpResult<T> result) throws JSONException {
        //默认解析公共的数据
        JSONObject resultObject = new JSONObject(response).getJSONObject("result");
        result.setResultCode(resultObject.getInt("resultCode"));
        if (resultObject.has("resultMessage"))
            result.setResultMessage(resultObject.getString("resultMessage"));
        //解析非公共的数据
        T e = decodeModel(response, result);
        result.setEntity(e);
        return e;
    }

    /**
     * 解析特有响应
     */
    public T decodeModel(String response, HttpResult<T> result) throws JSONException {
        return null;
    }

    private void addCommonParams() {
        //返回手机系统SDK版本(如16)
//        add(OS_VERSION, AndroidUtil.SDK_VERSION_CODE + "");
        //返回手机类型      ("1"为iOS, "2"为Android)
        add(PHONE_TYPE, OS_TYPE_ANDROID);
        //返回手机ID       (即IMEI,UUID)
//        add(PHONE_ID, AndroidUtil.getUUID());
        //返回APP版本名    (如1.5.8)
//        add(APP_VERSION, AndroidUtil.APP_VERSION_NAME);
        //返回APP名称      (如"思锐")
        add(APP_KEY, APP_NAME);
        //数据模型中，有这两个参数，才传, 用于session保活, 原理如下:
        //本来第一次请求网络时, 服务器创建一个session会话, 返回一个cookie
        //手机收到响应后持久化存储该cookie, 以后每次请求都上传该cookie, 即可保证session不会被销毁

        //第一次请求网络时, 上传input1和input2, 服务器创建一个session会话, 并与input1和input2关联, 即相当于token
        //当在指定时间内手机端没有新的请求时, 则服务器将session挂起, 当下次请求时, 服务器先从挂起的session列表查询是否
        //有与input1和input2关联的session, 若有则激活, 若无才重新创建session
//        String input1 = Customer.instance.input1;
//        String input2 = Customer.instance.input2;
//        if (!TextUtils.isEmpty(input1) && !TextUtils.isEmpty(input2)) {
//            add(INPUT1, input1);
//            add(INPUT2, input2);
//        }
    }
}
