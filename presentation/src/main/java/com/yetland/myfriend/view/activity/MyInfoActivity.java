package com.yetland.myfriend.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.databinding.ActivityMyInfoBinding;
import com.yetland.myfriend.model.SchoolMateModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.util.SharedPreferenceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInfoActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.tv_update)
    TextView tvUpdate;
    @Bind(R.id.bt_leave_message)
    Button btLeaveMessage;
    private UserModel userModel;
    private ActivityMyInfoBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_my_info);
        ButterKnife.bind(this,binding.getRoot());
        toolbar.setTitle(" ");
        tvTitle.setText("我的信息");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        tvUpdate.setVisibility(View.VISIBLE);
        userModel = (UserModel) SharedPreferenceUtil.readUserModel(MyInfoActivity.this);
        binding.setUserModel(userModel);
    }

    @OnClick({R.id.tv_update, R.id.bt_leave_message})
    public void onClick(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.tv_update:
                bundle.putSerializable("user",userModel);
                intent.putExtras(bundle);
                intent.setClass(this,UpdateUserInfoActivity.class);
                startActivityForResult(intent,1);
                break;
            case R.id.bt_leave_message:
                intent.setClass(this,LeaveMessageActivity.class);
                SchoolMateModel schoolMateModel = new SchoolMateModel();
                schoolMateModel.setToId(userModel.getId());
                schoolMateModel.setNick(userModel.getNick());
                bundle.putSerializable("schoolMateModel",schoolMateModel);
                bundle.putSerializable("userModel",userModel);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ResultCodeList.UPDATE_SUCCESS)
        {
            userModel = (UserModel) SharedPreferenceUtil.readUserModel(MyInfoActivity.this);
            binding.setUserModel(userModel);
        }
    }
}
