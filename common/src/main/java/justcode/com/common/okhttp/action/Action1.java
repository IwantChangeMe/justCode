package justcode.com.common.okhttp.action;

/**
 * 定义事件 接口 一个参数就是1，两个参数就是2
 * @param <T>
 */
public interface Action1<T> {
    void call(T t);
}
