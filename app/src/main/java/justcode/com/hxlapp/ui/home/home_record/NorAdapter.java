package justcode.com.hxlapp.ui.home.home_record;


import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

import justcode.com.common.db.entity.record.RecordEntity;
import justcode.com.hxlapp.R;
import justcode.com.hxlapp.base.BaseUIActivity;
import justcode.com.hxlapp.bussiness.record.RecordBiz;
import justcode.com.hxlapp.ui.record.RecordActivity;
import justcode.com.hxlapp.ui.ui_utils.IntentUtil.ActivityJumpUtil;


public class NorAdapter extends RecyclerView.Adapter<NorAdapter.MyViewHolder> {
    List<RecordEntity> list;
    Context context;
    NiftyDialogBuilder dialogBuilder;
    RecordBiz recordBiz;

    public NorAdapter(List<RecordEntity> list, Context context) {
        this.list = list;
        this.context = context;
        dialogBuilder = NiftyDialogBuilder.getInstance(context);
        recordBiz = new RecordBiz((BaseUIActivity) context);
    }

    public void update(List<RecordEntity> list0) {
        list = list0;
        notifyDataSetChanged();
    }

    //加入布局的地方,返回holder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item_record, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    //item处理的地方
    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        String title = list.get(position).getTitle() == null ? "默认标题" : list.get(position).getTitle();
        String time = list.get(position).getTimeStr() == null ? "未知时间" : list.get(position).getTimeStr();

        final String content = list.get(position).getConnent() == null ? "默认内容" : list.get(position).getConnent();

        holder.tvTitle.setText(title);
        holder.tvTime.setText(time);
        holder.tvContent.setText(content);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecordEntity recordEntity = list.get(position);
                ActivityJumpUtil.jump2record((Activity) context, RecordActivity.class, recordEntity);
            }
        });
        holder.cardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (list.get(position).getId() != -99)
                    dialogTips(position);
                return false;
            }
        });

    }

    private void dialogTips(final int position) {
        dialogBuilder
                .withTitle("提示")
                .withTitleColor("#ffffff")
                .withDividerColor("#11000000")
                .withMessage("确定要删除这条记录吗？")
                .withMessageColor("#ffffff")
                .withDialogColor("#005e37")
                .withIcon(context.getResources().getDrawable(R.mipmap.ic_launcher))
                .withDuration(500)
                .withEffect(Effectstype.RotateBottom)
                .withButton1Text("取消")
                .withButton2Text("确定")
                .isCancelableOnTouchOutside(true)
//                        .setCustomView(R.layout.custom_view, context)
                .setButton1Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogBuilder.dismiss();
                    }
                })
                .setButton2Click(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RecordEntity recordEntity = list.get(position);
                        recordBiz.delRecordEntity(recordEntity);
                        refushRecord();
                        dialogBuilder.dismiss();
                    }
                })
                .show();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //item初始化的地方
    //viewhoder
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvTime, tvContent;
        CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_record_title);
            tvTime = itemView.findViewById(R.id.tv_record_time);
            tvContent = itemView.findViewById(R.id.tv_record_content);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }

    /**
     * 刷新数据
     *
     * @return
     */
    public void refushRecord() {
        List<RecordEntity> recordEntityAll = recordBiz.getRecordEntityDesc();
        if (recordEntityAll != null) {
            if (recordEntityAll.size() > 20) {
                list = recordEntityAll.subList(0, 20);

            } else {
                list = recordEntityAll;
            }
        }
        notifyDataSetChanged();
    }
}
