package justcode.com.common.okhttp.function;


import java.util.concurrent.Callable;

/**
 * 定义任务接口，1表示1个参数
 */
public interface Function1<T> extends Callable<T> {
    @Override
    T call() throws Exception;
}
