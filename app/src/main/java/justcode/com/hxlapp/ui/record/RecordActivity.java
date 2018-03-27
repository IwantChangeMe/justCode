package justcode.com.hxlapp.ui.record;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import justcode.com.hxlapp.R;
import justcode.com.hxlapp.base.BaseUIActivity;
import justcode.com.hxlapp.ui.自定义view.SubmitButton;

public class RecordActivity extends BaseUIActivity {
    SubmitButton submitButton;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_record);
        setTitle("记事");

        submitView();
        submitButton = findViewById(R.id.sbtn_submit);


    }

    private void submitView() {

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitButton.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        submitButton.doResult(true);
                    }
                },1500);

            }
        });
        submitButton.setOnResultEndListener(new SubmitButton.OnResultEndListener() {
            @Override
            public void onResultEnd() {

            }
        });
    }


}
