package com.yetland.myfriend.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.model.SchoolMateModel;
import com.yetland.myfriend.model.UserModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yeliang on 2016/4/22.
 */
public class SchoolMateAdapter extends RecyclerView.Adapter<SchoolMateAdapter.SchoolMateHolder> {

    private OnItemClickListener onItemClickListener;
    private List<SchoolMateModel> schoolMateModels;

    private UserModel userModel;

    public SchoolMateAdapter(List<SchoolMateModel> schoolMateModels, UserModel userModel) {
        this.schoolMateModels = schoolMateModels;
        this.userModel = userModel;
    }



    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public SchoolMateHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new SchoolMateHolder(view);
    }

    @Override
    public void onBindViewHolder(SchoolMateHolder holder, int position) {

        holder.setData(position);
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return schoolMateModels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class SchoolMateHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.school_name)
        TextView schoolName;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.tv_belong)
        TextView tvBelong;

        public SchoolMateHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            SchoolMateModel schoolMateModel = schoolMateModels.get(position);
            schoolName.setText(schoolMateModel.getSchoolName());
            nick.setText(schoolMateModel.getNick());
            tvBelong.setText(schoolMateModel.getSex());
        }
    }
}
