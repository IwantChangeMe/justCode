package justcode.com.hxlapp.ui.home.record;


import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import justcode.com.hxlapp.R;
import justcode.com.hxlapp.bussiness.record.RecordEntity;

public class NorAdapter extends RecyclerView.Adapter<NorAdapter.MyViewHolder> {
    List<RecordEntity> list;
    Context context;

    public NorAdapter(List<RecordEntity> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void update(List<RecordEntity> list0) {
        list = list0;
        notifyDataSetChanged();
    }

    //加入布局的地方,返回holder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview_item_record, null);
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
                Toast.makeText(context, "点击了" + position, Toast.LENGTH_LONG).show();
            }
        });

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
}
