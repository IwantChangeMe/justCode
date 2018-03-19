package justcode.com.common.loading;


import justcode.com.common.BaseActivity;

public class NormalLoading extends BaseLoading {
    public NormalLoading(BaseActivity activity) {
        super(activity);
    }

    @Override
    public void show() {
        super.show();
        // TODO: 2018/3/19  展示进度条
    }

    @Override
    public void disMiss() {
        super.disMiss();
        // TODO: 2018/3/19  取消进度条
    }

    /**
     * 进度调显示的回调
     */
    @Override
    public void onShow() {

    }


    /**
     * 进度条隐藏的回调
     */
    @Override
    public void onDismiss() {

    }
}
