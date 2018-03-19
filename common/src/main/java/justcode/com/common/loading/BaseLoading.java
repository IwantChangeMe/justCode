package justcode.com.common.loading;

import justcode.com.common.BaseActivity;

/**
 * 所有loading的基类
 */
public abstract class BaseLoading {
    private BaseActivity activity;
    public static boolean isShowing = false;

    public BaseLoading(BaseActivity activity) {
        this.activity = activity;
    }

    /**
     * 显示进度条
     */
    public void show() {
        isShowing = true;
        onShow();
    }

    /**
     * 隐藏进度条
     */
    public void disMiss() {
        isShowing = false;
        onDismiss();
    }

    /**
     * 进度条显示的回调
     */
    public abstract void onShow();

    /**
     * 进度条消失时的回调
     */
    public abstract void onDismiss();
}
