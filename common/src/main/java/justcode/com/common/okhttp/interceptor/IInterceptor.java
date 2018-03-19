package justcode.com.common.okhttp.interceptor;


/**
 * 抽象拦截器，定义开始和结束，具体实现，实现类完成
 */
public interface IInterceptor {

    void runOnStart();//开始

    void runOnComplete();//完成(无论，结果如果，只要完成就执行)

}
