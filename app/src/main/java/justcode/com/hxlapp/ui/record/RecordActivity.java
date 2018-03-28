package justcode.com.hxlapp.ui.record;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import justcode.com.common.db.entity.record.RecordEntity;
import justcode.com.common.okhttp.action.Action1;
import justcode.com.hxlapp.R;
import justcode.com.hxlapp.base.BaseUIActivity;
import justcode.com.hxlapp.bussiness.record.RecordBiz;
import justcode.com.hxlapp.ui.common_ui.自定义view.SubmitButton;

public class RecordActivity extends BaseUIActivity {
    SubmitButton submitButton;
    TextView tvDate;
    EditText etTitle;
    EditText etContent;
    RecordBiz recordBiz;

    boolean isHasRecord = false;
    RecordEntity recordEntity;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_record);
        setTitle("记事");
        initView();
        recordBiz = new RecordBiz(this);
        Intent intent = getIntent();
        recordEntity = intent.getParcelableExtra("RecordEntity");
        if (recordEntity != null) {
            isHasRecord = true;
            etTitle.setText(recordEntity.getTitle());
            etContent.setText(recordEntity.getConnent());
        }


    }

    private void initView() {
        submitView();
        tvDate = findViewById(R.id.tv_record_date);
        etTitle = findViewById(R.id.et_record_title);
        etContent = findViewById(R.id.et_record_content);
        tvDate.setText(getCurrentTime() + "时");
    }

    private void submitView() {
        submitButton = findViewById(R.id.sbtn_submit);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getUserInput(new Action1<RecordEntity>() {
                    @Override
                    public void call(RecordEntity recordEntity) {
                        if (isHasRecord) {
                            recordBiz.updateRecordEntity(recordEntity);
                        } else {
                            recordBiz.write2DB(recordEntity);
                        }

                        submitButton.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                submitButton.doResult(true);
                            }
                        }, 1500);
                    }
                });
            }
        });
        submitButton.setOnResultEndListener(new SubmitButton.OnResultEndListener() {
            @Override
            public void onResultEnd() {
                finish();
            }
        });
    }


    public void getUserInput(Action1<RecordEntity> action1) {
        String title = null;
        String content = null;
        if (etTitle != null && etTitle.getText() != null && !TextUtils.isEmpty(etTitle.getText().toString())) {
            title = etTitle.getText().toString();
        }
        if (etContent != null && etContent.getText() != null && !TextUtils.isEmpty(etContent.getText().toString())) {
            content = etContent.getText().toString();
        }
        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(content)) {
            Toast.makeText(RecordActivity.this, "请输入标题和内容", Toast.LENGTH_LONG).show();
            submitButton.reset();
            return;
        }
        long timelong = System.currentTimeMillis();
        String timeStr = getCurrentTime(timelong);

        if (recordEntity == null) {
            recordEntity = new RecordEntity(null, title, content, timeStr, timelong);
        } else {
            recordEntity.setTitle(title);
            recordEntity.setConnent(content);
            recordEntity.setDate(timelong);
            recordEntity.setTimeStr(timeStr);
        }
        action1.call(recordEntity);
    }

    public String getCurrentTime(long timelong) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");// HH:mm:ss
        //获取当前时间
        Date date = new Date(timelong);
        return simpleDateFormat.format(date);
    }

    public String getCurrentTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH");// HH:mm:ss
        //获取当前时间
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }
}
