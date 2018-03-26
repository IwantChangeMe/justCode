package justcode.com.hxlapp.ui.home.record;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import justcode.com.common.db.entity.record.RecordEntity;
import justcode.com.hxlapp.R;
import justcode.com.hxlapp.bussiness.record.RecordBiz;
import justcode.com.hxlapp.ui.home.MainActivity;


/**
 * 记事fragment
 */

public class RecordFragment extends Fragment {
    static final String TAG = "RecordFragment";

    List<RecordEntity> list = new ArrayList<>();
    RecyclerView recyclerView;
    NorAdapter norAdapter;
    RecordBiz recordBiz;
    public RecordFragment(MainActivity activity) {
        //获取需要展示的数据
          list = getRecordEntity();
          recordBiz = new RecordBiz(activity);
    }

    public void updateRecord(List<RecordEntity> list0) {
        norAdapter.update(list0);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            Log.d(TAG, "onCreateView");
            view = inflater.inflate(R.layout.fragment_record, container, false);
            initView();
        }
        return view;
    }

    private void initView() {
        recyclerView = view.findViewById(R.id.rv_record);
        //设置LinearLayoutManager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL); //加上这句 就可以设置方向
        recyclerView.setLayoutManager(layoutManager);

        //添加Android自带的分割线
//        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        //添加数据
        norAdapter = new NorAdapter(list, getContext());
        //加入adapter
        recyclerView.setAdapter(norAdapter);

        RefreshLayout refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        refreshLayout.setEnableLoadmoreWhenContentNotFull(true);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                k = 0;
                updateRecord(getRecordEntity());
            }
        });
      refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
          @Override
          public void onLoadmore(RefreshLayout refreshlayout) {
              refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
              updateRecord(getRecordEntity());
          }
      });

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged:" + hidden);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        Log.d(TAG, "isVisibleToUser:" + isVisibleToUser);
    }

    int k;


    public List<RecordEntity> getRecordEntity() {

        int m = k;
        for (int i = k; i < k + 10; i++) {
            list.add(new RecordEntity("第" + i + "套", "内容" + i, "2018年1月" + i + "日", 0));
            m++;
        }
        k = m;
//        return  recordBiz.getRecordEntityAll();
        return list;
    }
}
