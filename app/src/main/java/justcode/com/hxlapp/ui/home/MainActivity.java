package justcode.com.hxlapp.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.widget.RadioGroup;

import justcode.com.hxlapp.R;
import justcode.com.hxlapp.base.BaseUIActivity;
import justcode.com.hxlapp.ui.home.record.RecordFragment;

public class MainActivity extends BaseUIActivity  implements NavigationView.OnNavigationItemSelectedListener{

    AccountingFragment accountingFragment;
    RecordFragment recordFragment;
    ToolFragment toolFragment;

    RadioGroup radioGroup;

    FragmentManager fm;
    FragmentTransaction ft;
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState, R.layout.activity_main);

        radioGroup = (RadioGroup) findViewById(R.id.rg_main);

        // 初始化fragmen管理器
        fm = getSupportFragmentManager();
        if (recordFragment == null)
            recordFragment = new RecordFragment();
        replace(recordFragment);
        initListener();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                        if (recordFragment == null)
                            recordFragment = new RecordFragment();
                        replace(recordFragment);
                        break;
                    case R.id.radio2:
                        if (accountingFragment == null)
                            accountingFragment = new AccountingFragment();
                        replace(accountingFragment);
                        break;
                    case R.id.radio3:
                        if (toolFragment == null)
                            toolFragment= new ToolFragment();
                        replace(toolFragment);
                        break;

                }
            }
        });


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
