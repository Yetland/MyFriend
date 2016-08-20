package com.yetland.myfriend.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.model.ActivityModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yeliang on 2016/4/19.
 */
public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityHolder> {


    private List<ActivityModel> activityModels;
    private onItemClickListener onItemClickListener;

    public ActivityAdapter(List<ActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    @Override
    public ActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity, parent, false);


        return new ActivityHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityHolder holder, int position) {
        holder.setData(activityModels.get(position));
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(position));
    }

    @Override
    public int getItemCount() {
        return activityModels.size();
    }

    public void setOnItemClickListener(ActivityAdapter.onItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    class ActivityHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.school_name)
        TextView schoolName;
        @Bind(R.id.activity_title)
        TextView activityTitle;
        @Bind(R.id.content_message)
        TextView contentMessage;
        @Bind(R.id.time)
        TextView time;
        @Bind(R.id.creator_name)
        TextView creatorName;

        public ActivityHolder(View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(ActivityModel activityModel) {
            schoolName.setText(activityModel.getSchoolName());
            activityTitle.setText(activityModel.getTitle());
            contentMessage.setText(activityModel.getContentMessage());
            time.setText(activityModel.getCreateTime());
            creatorName.setText(activityModel.getNick());
        }
    }
}
