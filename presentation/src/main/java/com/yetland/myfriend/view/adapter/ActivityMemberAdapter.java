package com.yetland.myfriend.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.model.ActivityMember;
import com.yetland.myfriend.model.UserModel;

import java.util.Collections;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yeliang on 2016/4/21.
 */
public class ActivityMemberAdapter extends RecyclerView.Adapter<ActivityMemberAdapter.UserItemHolder> {

    private static final String TAG = "ActivityMemberAdapter";
    private OnItemClickListener onItemClickListener;
    private List<ActivityMember> activityMembers;
    private UserModel userModel;
    private boolean creator = false;
    private boolean member = false;

    public ActivityMemberAdapter(List<ActivityMember> activityMembers, UserModel userModel) {
        this.activityMembers = activityMembers;
        this.userModel = userModel;
        // 倒叙操作
        Collections.reverse(activityMembers);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public UserItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new UserItemHolder(view);
    }

    @Override
    public void onBindViewHolder(UserItemHolder holder, int position) {

        holder.setData(position);
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return activityMembers.size();
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public boolean isMember() {
        return member;
    }

    public void setMember(boolean member) {
        this.member = member;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class UserItemHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.school_name)
        TextView schoolName;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.tv_belong)
        TextView tvBelong;

        public UserItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            ActivityMember activityMember = activityMembers.get(position);
            schoolName.setText(activityMember.getSchoolName());
            nick.setText(activityMember.getNick());
            if (activityMember.getCreatorId() == activityMember.getMemberId()) {
                if (activityMember.getMemberId() == userModel.getId()) {
                    tvBelong.setText("我是发起人");
                    setCreator(true);
                    Log.e(TAG, "setData:isCreator:" + isCreator());
                } else
                    tvBelong.setText("发起人");
            } else if (activityMember.getMemberId() == userModel.getId()) {
                tvBelong.setText("我");
                setMember(true);
                Log.e(TAG, "setData:isMember:" + isMember());
            }
        }
    }

}
