package justcode.com.common.okhttp;

import android.content.Context;
import android.os.Handler;
import android.util.Pair;

import java.util.ArrayList;
import java.util.List;

import justcode.com.common.okhttp.action.Action1;
import justcode.com.common.okhttp.function.Function1;
import justcode.com.common.okhttp.interceptor.IInterceptor;
import justcode.com.common.okhttp.interceptor.InterceptorUtil;


/**
 * 拦截器，--抽象类
 * 拦截器是动态拦截Action调用的对象，Action执行的前后执行一段代码，也可以在一个Action
 * 这里，把一次请求当成一个Action,在这个请求执行前后，执行某个方法
 */
public abstract class InterceptorHttpAction<T, S> {

    protected Handler mHandler = new Handler();

    private List<Runnable> startAction = null;              //请求网络开始前调用本任务列表
    private List<Runnable> completAction = null;            //请求网络结束后调用本任务列表(json解析前)
    private List<Action1<HttpResult>> resultAction = null;  //请求网络结束后调用本任务列表(json解析后)

    /**
     * 拦截器，一个事件，开始和结束的时候，添加方法（请求开始，请求结束，中间过程加载动画）
     */
    public InterceptorHttpAction<T, S> addInterceptor(final IInterceptor action) {
        addAction(new Function1<Pair<Runnable, Runnable>>() {
            @Override
            public Pair<Runnable, Runnable> call() {
                return new Pair<Runnable, Runnable>(new Runnable() {
                    @Override
                    public void run() {
                        action.runOnStart();
                    }
                }, new Runnable() {
                    @Override
                    public void run() {
                        action.runOnComplete();
                    }
                });
            }
        });
        return this;
    }

    /**
     * 添加一个返回值为 "任务对" 的任务, 并将任务对的第一个任务添加到 开始任务列表中, 将任务对的第二个任务添加到 结束任务列表中
     */
    private InterceptorHttpAction<T, S> addAction(Function1<Pair<Runnable, Runnable>> actionBuilder) {
        Pair<Runnable, Runnable> actions = null;
        try {
            actions = actionBuilder.call();
        } catch (Exception e) {
            if (e.getMessage() != null)
                LogHttp.log(e.getMessage());
            e.printStackTrace();
        }
        return addAction(actions.first, actions.second);
    }

    /**
     * 将指定的两个任务分别添加到 开始任务列表 和 结束任务列表 中
     */
    private InterceptorHttpAction<T, S> addAction(Runnable start, Runnable complet) {
        return addActionOnStart(start).addActionOnComplet(complet);
    }

    /**
     * 将指定的任务添加到 开始任务列表 中
     */
    private InterceptorHttpAction<T, S> addActionOnStart(Runnable action) {
        if (action == null) return this;
        if (startAction == null) startAction = new ArrayList<>();
        startAction.add(action);
        return this;
    }

    /**
     * 将指定的任务添加到 结束任务列表 中
     */
    private InterceptorHttpAction<T, S> addActionOnComplet(Runnable action) {
        if (action == null) return this;
        if (completAction == null) completAction = new ArrayList<>();
        completAction.add(action);
        return this;
    }

    /**
     * 将指定任务添加到 结果处理任务列表中
     */
    private InterceptorHttpAction<T, S> addActionOnResult(Action1<HttpResult> action) {
        if (action == null) return this;
        if (resultAction == null) resultAction = new ArrayList<>();
        resultAction.add(action);
        return this;
    }

    /**
     * 执行 开始任务列表 中的所有任务
     */
    protected void runOnStart() {
        if (startAction != null) {
            for (Runnable action : startAction) {
                mHandler.post(action);
            }
        }
    }

    /**
     * 执行 结束任务列表 中的所有任务
     */
    protected void runOnComplet() {
        if (completAction != null) {
            for (Runnable action : completAction) {
                mHandler.post(action);
            }
        }
    }

    /**
     * 执行 结果处理任务列表 中的所有任务(即对同一个请求结果, 不同的任务有不同的处理方法)
     */
    protected void runOnResult(final HttpResult result) {
        if (resultAction != null) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    for (Action1<HttpResult> action : resultAction) {
                        action.call(result);
                    }
                }
            });
        }
    }

    /**
     * 设置请求成功时的回调任务, 将之添加到结果处理任务列表中
     */
    public InterceptorHttpAction<T, S> onSuccess(final Action1<HttpResult> action) {
        addActionOnResult(new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                if (result.getResultCode() == 0) action.call(result);
            }
        });
        return this;
    }

    /**
     * 设置请求失败时的回调任务, 将之添加到结果处理任务列表中
     */
    public InterceptorHttpAction<T, S> onFail(final Action1<HttpResult> action) {
        addActionOnResult(new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                if (result.getResultCode() != 0) action.call(result);
            }
        });
        return this;
    }

    /**
     * 创建请求失败时, 弹Toast的任务
     */
    public InterceptorHttpAction<T, S> onFailToast(Context context) {
        return onFail(InterceptorUtil.buildToastAction(context));
    }

    /**
     * 执行请求
     */
    public abstract void execute();
}
