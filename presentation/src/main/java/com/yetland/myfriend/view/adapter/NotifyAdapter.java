package com.yetland.myfriend.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.model.SchoolMateInvitation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yeliang on 2016/4/26.
 */
public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyHolder> {

    private List<SchoolMateInvitation> schoolMateInvitations;
    private OnItemClickListener onItemClickListener;
    private OnAcceptClickListener onAcceptClickListener;

    public NotifyAdapter(List<SchoolMateInvitation> schoolMateInvitations) {
        this.schoolMateInvitations = schoolMateInvitations;
    }

    @Override
    public NotifyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify, parent, false);

        return new NotifyHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(NotifyHolder holder, int position) {
        holder.setData(position);
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return schoolMateInvitations.size();
    }

    public void setOnAcceptClickListener(OnAcceptClickListener onAcceptClickListener) {
        this.onAcceptClickListener = onAcceptClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public interface OnAcceptClickListener {
        void onAcceptClick(int position);
    }

    class NotifyHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.bt_reply)
        Button btAccept;
        @Bind(R.id.school_name)
        TextView schoolName;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.time)
        TextView time;

        int position;

        public NotifyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            this.position = position;
            SchoolMateInvitation schoolMateInvitation = schoolMateInvitations.get(position);
            schoolName.setText(schoolMateInvitation.getSchoolName());
            nick.setText(schoolMateInvitation.getNick());
            time.setText(schoolMateInvitation.getTime());
            String status = schoolMateInvitation.getStatus();
            status = status.replaceAll(" ", "");
            if (status.equals("done")) {
                btAccept.setText("已同意");
                btAccept.setEnabled(false);
            }
        }

        @OnClick(R.id.bt_reply)
        public void onClick() {
            onAcceptClickListener.onAcceptClick(position);
        }
    }
}
