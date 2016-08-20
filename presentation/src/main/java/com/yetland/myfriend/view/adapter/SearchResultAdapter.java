package com.yetland.myfriend.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.model.SearchUserModel;
import com.yetland.myfriend.model.UserModel;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yeliang on 2016/4/25.
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.SearchResultHolder> {

    private OnItemClickListener onItemClickListener;
    private List<SearchUserModel> searchUserModels;
    private UserModel userModel;
    private List<Integer> schoolMateIds;

    public SearchResultAdapter(List<SearchUserModel> searchUserModels, UserModel userModel, List<Integer> schoolMateIds) {
        this.searchUserModels = searchUserModels;
        this.userModel = userModel;
        this.schoolMateIds = schoolMateIds;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    @Override
    public SearchResultHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user, parent, false);
        return new SearchResultHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchResultHolder holder, int position) {

        holder.setData(position);
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return searchUserModels.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    class SearchResultHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.school_name)
        TextView schoolName;
        @Bind(R.id.nick)
        TextView nick;
        @Bind(R.id.tv_belong)
        TextView tvBelong;

        public SearchResultHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(int position) {
            SearchUserModel schoolMateModel = searchUserModels.get(position);
            schoolName.setText(schoolMateModel.getSchoolName());
            nick.setText(schoolMateModel.getNick());
            if (schoolMateModel.getId() == userModel.getId()) {
                tvBelong.setText("我");
            } else if (schoolMateIds.contains(schoolMateModel.getId())) {
                tvBelong.setText("好友");
            }
        }
    }
}
