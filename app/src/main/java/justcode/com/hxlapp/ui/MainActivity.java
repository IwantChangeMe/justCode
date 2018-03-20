package justcode.com.hxlapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;

import justcode.com.hxlapp.R;
import justcode.com.hxlapp.base.BaseUIActivity;

public class MainActivity extends BaseUIActivity {


    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
