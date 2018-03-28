package justcode.com.hxlapp.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import justcode.com.hxlapp.R;
import justcode.com.hxlapp.base.BaseUIActivity;
import justcode.com.hxlapp.ui.home.home_record.RecordFragment;
import justcode.com.hxlapp.ui.record.RecordActivity;
import justcode.com.hxlapp.ui.ui_utils.IntentUtil.ActivityJumpUtil;
import justcode.com.hxlapp.ui.ui_utils.animation_util.AnimationUtil;

public class MainActivity extends BaseUIActivity implements NavigationView.OnNavigationItemSelectedListener {

    AccountingFragment accountingFragment;
    RecordFragment recordFragment;
    ToolFragment toolFragment;

    RadioGroup radioGroup;

    FloatingActionButton fab;

    FragmentManager fm;
    FragmentTransaction ft;
    int currentFragment;
    TextView title;

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.rg_main);

        fab = findViewById(R.id.fab);

        title = findViewById(R.id.title);
        title.setText("记事");
        // 初始化fragmen管理器
        fm = getSupportFragmentManager();
        if (recordFragment == null)
            recordFragment = new RecordFragment(this);
        replace(recordFragment);
        initListener();


//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
    }

    private void replace(Fragment fragment) {
        ft = fm.beginTransaction();
        ft.replace(R.id.fl_root, fragment);
        ft.commit();
    }

    private void initListener() {
        radioGroup.check(R.id.radio1);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radio1:
                        currentFragment = 0;
                        title.setText("记事");
                        showFloatButton();
                        if (recordFragment == null)
                            recordFragment = new RecordFragment(MainActivity.this);
                        replace(recordFragment);
                        break;
                    case R.id.radio2:
                        currentFragment = 1;
                        title.setText("计划");
                        showFloatButton();
                        if (accountingFragment == null)
                            accountingFragment = new AccountingFragment();
                        replace(accountingFragment);
                        break;
                    case R.id.radio3:
                        title.setText("工具");
                        currentFragment = 2;
                        hideFloatButton();
                        if (toolFragment == null)
                            toolFragment = new ToolFragment();
                        replace(toolFragment);
                        break;

                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (currentFragment){
                    case 0:
                        ActivityJumpUtil.jump(MainActivity.this, RecordActivity.class);
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    private void hideFloatButton() {

        if (fab.getVisibility() != View.INVISIBLE) {
            fab.setVisibility(View.INVISIBLE);
            AnimationUtil.newAnimation(this, fab)
                    .Scale(AnimationUtil.ScaleSmall)
                    .Alpha(AnimationUtil.AlphaHide)
                    .play();
        }
    }

    private void showFloatButton() {
        if (fab.getVisibility() != View.VISIBLE) {
            fab.setVisibility(View.VISIBLE);
            AnimationUtil.newAnimation(this, fab)
                    .Scale(AnimationUtil.ScaleBig)
                    .Alpha(AnimationUtil.AlphaShow)
                    .play();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
