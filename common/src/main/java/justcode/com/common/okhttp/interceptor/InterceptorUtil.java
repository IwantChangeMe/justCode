package justcode.com.common.okhttp.interceptor;


import android.content.Context;
import android.widget.Toast;

import justcode.com.common.loading.BaseLoading;
import justcode.com.common.okhttp.HttpResult;
import justcode.com.common.okhttp.action.Action1;

public class InterceptorUtil {

    public static Action1<HttpResult> buildToastAction(final Context context) {
        return new Action1<HttpResult>() {
            @Override
            public void call(HttpResult result) {
                Toast.makeText(context,result.getResultMessage(),Toast.LENGTH_LONG).show();
            }
        };
    }

    public static IInterceptor buildCircleProgressbar(final BaseLoading progressbar) {
        return new IInterceptor() {
            @Override
            public void runOnStart() {
                if (!BaseLoading.isShowing) progressbar.show();
            }

            @Override
            public void runOnComplete() {
                if (BaseLoading.isShowing) progressbar.disMiss();
            }
        };
    }
}
