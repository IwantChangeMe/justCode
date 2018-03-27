package justcode.com.hxlapp.ui.ui_utils.IntentUtil;


import android.app.Activity;
import android.content.Intent;

import justcode.com.common.db.entity.record.RecordEntity;

public class ActivityJumpUtil {

    public static void jump(Activity from, Class to) {
        Intent intent = new Intent();
        intent.setClass(from, to);
        from.startActivity(intent);
    }
    public static void jump2record(Activity from, Class to, RecordEntity entity){
        Intent intent = new Intent();
        intent.setClass(from, to);
        intent.putExtra("RecordEntity",entity);
        from.startActivity(intent);
    }
}
