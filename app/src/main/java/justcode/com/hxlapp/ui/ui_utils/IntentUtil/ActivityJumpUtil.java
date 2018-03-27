package justcode.com.hxlapp.ui.ui_utils.IntentUtil;


import android.app.Activity;
import android.content.Intent;

public class ActivityJumpUtil {

    public static void jump(Activity from, Class to) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        from.startActivity(intent);
    }
}
