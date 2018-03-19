package justcode.com.hxlapp.base;


import android.util.Log;

import justcode.com.common.BaseApplication;

public class BaseUIApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Application","onCreate");
    }
}
