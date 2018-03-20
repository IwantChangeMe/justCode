package justcode.com.hxlapp.base;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import justcode.com.common.BaseActivity;
import justcode.com.hxlapp.R;

public class BaseUIActivity extends BaseActivity {
    private LinearLayout llRoot;    //根布局
    private View actionBar;         //标题栏布局
    private View contentView;       //内容布局
    private RelativeLayout rlBack;  //返回箭头
    private TextView tvTitle;       //标题
    private TextView tvRight;       //右边文字
    private ImageView ivRight;      //右边图标

    private View rootView;
    private ImageView ivBack;
    protected void onCreate(@Nullable Bundle savedInstanceState, int layoutId) {
        super.onCreate(savedInstanceState);
        //隐藏自带的actionbar
        View decorView = getWindow().getDecorView();
        int option = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(option);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //设置根布局
        setContentView(R.layout.activity_root);
        //设置标题栏
        llRoot = (LinearLayout) findViewById(R.id.ll_root);
        addActionBar(setMyActionBar());
        //设置内容布局
        View inflate = View.inflate(this, layoutId, null);
        addContentView(inflate);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    /**
     * 添加contentview
     */
    private void addContentView(View view) {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (llRoot != null && view != null) {
            contentView = view;
            llRoot.addView(view, lp);
        }
    }

    /**
     * 添加actionbar
     */
    private void addActionBar(View view) {
        if (llRoot != null && view != null) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            actionBar = view;
            llRoot.addView(view, lp);
            //默认隐藏, 只有当setTitle()时才显示
            actionBar.setVisibility(View.GONE);
        }
    }

    /**
     * 获取acitonbar
     */
    protected View setMyActionBar() {
        View view = getLayoutInflater().inflate(R.layout.actionbar_default_layout, null);
        rootView = view.findViewById(R.id.rl_rootlayout);
        ivBack = (ImageView) view.findViewById(R.id.iv_title_back);
        rlBack = (RelativeLayout) view.findViewById(R.id.image_left);
        tvTitle = (TextView) view.findViewById(R.id.textView_title);
        tvRight = (TextView) view.findViewById(R.id.tv_actionbar_right);
        ivRight = (ImageView) view.findViewById(R.id.iv_right);

        rlBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackClick();
            }
        });
        tvRight.setVisibility(View.GONE);   //默认隐藏, 只有设置文字内容时, 才显示
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        return view;
    }

    /**
     * 后退箭头点击事件, 默认finish当前界面
     */
    public void onBackClick() {
        finish();
    }

    /**
     * 隐藏标题栏
     */
    public void hideActionBar() {
        actionBar.setVisibility(View.GONE);
    }

    /**
     * 设置title
     */
    public void setTitle(String title) {
        if (tvTitle != null && !TextUtils.isEmpty(title)) {
            actionBar.setVisibility(View.VISIBLE);
            tvTitle.setText(title);
        } else {
            actionBar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边文字
     */
    public void setRight(String str) {
        if (tvRight == null) return;
        if (!TextUtils.isEmpty(str)) {
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(str);
        } else {
            tvRight.setVisibility(View.GONE);
        }
    }

    /**
     * 设置右边图标
     * resId 资源文件id
     */
    public void setRightImg(int resId) {
        if (ivRight == null) return;
        ivRight.setImageResource(resId);
        ivRight.setVisibility(View.VISIBLE);
    }

    /**
     * 显示左边按钮
     */
    public void showBack() {
        if (rlBack != null) rlBack.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏后退箭头
     */
    public void hideBack() {
        if (rlBack != null) rlBack.setVisibility(View.INVISIBLE);
    }

    /**
     * 显示右边文字
     */
    public void showRight() {
        if (tvRight != null) tvRight.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏右边文字
     */
    public void hideRight() {
        if (tvRight != null) tvRight.setVisibility(View.GONE);
    }

    /**
     * 获取根布局
     */
    public LinearLayout getMyRootView() {
        return llRoot;
    }

    /**
     * 获取actionbar
     */
    public View getMyActionBar() {
        return actionBar;
    }

    /**
     * 获取contentView
     */
    public View getMyContentView() {
        return contentView;
    }
}
