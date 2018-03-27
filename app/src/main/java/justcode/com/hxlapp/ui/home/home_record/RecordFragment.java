package justcode.com.hxlapp.ui.home.home_record;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

@SuppressLint("ValidFragment")
public class RecordFragment extends Fragment {
    static final String TAG = "RecordFragment";
    MainActivity activity;
    List<RecordEntity> list = new ArrayList<>();
    RecyclerView recyclerView;
    NorAdapter norAdapter;
    RecordBiz recordBiz;

    @SuppressLint("ValidFragment")
    public RecordFragment(MainActivity activity) {
        this.activity = activity;
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
        refreshLayout.setEnableLoadmoreWhenContentNotFull(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                k = 0;
                updateRecord(refushRecordEntity());
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(2000/*,false*/);//传入false表示加载失败
                if (list.size() < 20) {
                    Toast.makeText(activity, "没有更多数据了", Toast.LENGTH_LONG).show();
                    return;
                }
                updateRecord(loadMoreRecordEntity());
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        k = 0;
        updateRecord(refushRecordEntity());
        Log.d(TAG, "onResume");
    }

    int k;

    /**
     * 刷新数据
     *
     * @return
     */
    public List<RecordEntity> refushRecordEntity() {
        List<RecordEntity> recordEntityAll = recordBiz.getRecordEntityDesc();
        if (recordEntityAll != null) {
            if (recordEntityAll.size() > 20) {
                list = recordEntityAll.subList(0, 20);
                k = 20;
            } else {
                list = recordEntityAll;
                k = list.size();
            }
        }
        return list;
    }

    /**
     * 加载更多数据
     */
    public List<RecordEntity> loadMoreRecordEntity() {
        List<RecordEntity> recordEntityAll = recordBiz.getRecordEntityDesc();
        if (recordEntityAll != null) {
            if (recordEntityAll.size() > 20) {
                list.addAll(recordEntityAll.subList(k, k + 20));
                k = k + 20;
            }
        }
        return list;
    }

}
