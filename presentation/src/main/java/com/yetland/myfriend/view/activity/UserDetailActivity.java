package com.yetland.myfriend.view.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.databinding.ActivityUserDetailBinding;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.SchoolMateModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.UserDetailPresenter;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.widget.CustomProgressDialog;
import com.yetland.myfriend.view.UserDetailView;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BaseActivity implements UserDetailView {

    private static final String TAG = "UserDetailActivity";
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.bt_add_as_school_mate)
    Button btAddAsSchoolMate;
    @Bind(R.id.bt_delete_school_mate)
    Button btDeleteSchoolMate;
    @Bind(R.id.bt_delete_activity_member)
    Button btDeleteActivityMember;
    @Inject
    RestApi restApi;
    @Bind(R.id.bt_leave_message)
    Button btLeaveMessage;
    private ActivityModel activityModel;
    private UserModel userModel;
    private SchoolMateModel mSchoolMateModel;
    private ActivityUserDetailBinding binding;
    private boolean friend = false;
    private boolean myself = false;
    private boolean creator = false;
    private List<SchoolMateModel> schoolMateModels;
    private CustomProgressDialog dialog;
    private UserDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail);

        ButterKnife.bind(this, binding.getRoot());
        toolbar.setTitle(" ");
        tvTitle.setText("详细信息");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        // 当前用户信息
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");
        // 从activity详细点击进入的activityModel信息
        activityModel = (ActivityModel) getIntent().getSerializableExtra("activityModel");
        // 详细信息显示的格式
        mSchoolMateModel = (SchoolMateModel) getIntent().getSerializableExtra("schoolMateModel");
        // 数据绑定
        binding.setSchoolMateModel(mSchoolMateModel);

        init();
    }

    private void init() {
        Log.e(TAG, "init:");
        setFriend(false);
        setCreator(false);
        setMyself(false);
        btDeleteSchoolMate.setVisibility(View.GONE);
        btAddAsSchoolMate.setVisibility(View.GONE);
        btDeleteActivityMember.setVisibility(View.GONE);
        // 此处判断是否从活动界面跳转而来，如果不是，则不会有删除成员的选项
        if (activityModel == null) {
            setCreator(false);
        } else if (userModel.getId() == activityModel.getCreatorId()) {
            setCreator(true);
        }
        //从内存中获取我的校友列表
        schoolMateModels = new ArrayList<>();
        schoolMateModels = SharedPreferenceUtil.readSchoolMateList(this);
        Log.e(TAG, "init:" + schoolMateModels.toString());
        Log.e(TAG, "init:isFriend:" + isFriend() + "\tisCreator:" + isCreator() + "\tisMyself:" + isMyself());
        if (schoolMateModels != null && schoolMateModels.size() != 0) {
            for (SchoolMateModel schoolMateModel : schoolMateModels) {
                // 当前显示的SchoolMateModel 与我的好友列表对比
                if (schoolMateModel.getToId() == mSchoolMateModel.getToId()) {
                    setFriend(true);
                }
            }
        }

        // 判断是否是自己
        if (mSchoolMateModel.getToId() == userModel.getId()) {
            setMyself(true);
        }

        Log.e(TAG, "init:isFriend:" + isFriend() + "\tisCreator:" + isCreator() + "\tisMyself:" + isMyself());
        // 如果是我自己，则不做任何操作
        if (!isMyself()) {
            // 如果是创建者，有删除成员的权利
            if (isCreator()) {
//                btDeleteActivityMember.setVisibility(View.VISIBLE);
            }
            //如果是朋友则有删除的权利
            if (isFriend()) {
                btDeleteSchoolMate.setVisibility(View.VISIBLE);
            } else {
                // 不是朋友，则有添加朋友的权利
                btAddAsSchoolMate.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerApiComponent.builder()
                .appComponent(appComponent)
                .apiModule(new ApiModule(this))
                .build()
                .inject(this);
    }

    public boolean isCreator() {
        return creator;
    }

    public void setCreator(boolean creator) {
        this.creator = creator;
    }

    public boolean isFriend() {
        return friend;
    }

    public void setFriend(boolean friend) {

        this.friend = friend;
    }

    public boolean isMyself() {
        return myself;
    }

    public void setMyself(boolean myself) {
        this.myself = myself;
    }

    @OnClick({R.id.bt_add_as_school_mate, R.id.bt_delete_school_mate, R.id.bt_delete_activity_member, R.id.bt_leave_message})
    public void onClick(View view) {
        presenter = new UserDetailPresenter(restApi, this, this);
        switch (view.getId()) {
            case R.id.bt_add_as_school_mate:
                presenter.sendSchoolMateInvitation(userModel.getId(), mSchoolMateModel.getToId());
                break;
            case R.id.bt_delete_school_mate:
                presenter.deleteSchoolMate(userModel.getId(), mSchoolMateModel.getToId());
                break;
            case R.id.bt_delete_activity_member:
                // 此处删除成员操作仅限于创建人，所以userModel的Id和Activity的CreatorId一致
                presenter.deleteActivityMember(activityModel.getActivityId(), userModel.getId(), mSchoolMateModel.getToId());
                break;
            case R.id.bt_leave_message:
                Intent intent = new Intent(this, LeaveMessageActivity.class);
                Bundle bundle = new Bundle();

                bundle.putSerializable("schoolMateModel", mSchoolMateModel);
                intent.putExtras(bundle);

                startActivity(intent);
                break;
        }
    }

    @Override
    public void sendInvitationSuccess() {

    }

    @Override
    public void sendInvitationFailed() {

    }

    @Override
    public void deleteSchoolMateSuccess() {
        Log.e(TAG, "deleteSchoolMateSuccess:Before" + schoolMateModels.size());
        for (int i = 0; i < schoolMateModels.size(); i++) {
            if (schoolMateModels.get(i).getToId() == mSchoolMateModel.getToId()) {
                schoolMateModels.remove(i);
            }
        }

        Log.e(TAG, "deleteSchoolMateSuccess:" + schoolMateModels.size());
        SharedPreferenceUtil.saveSchoolMateList(this, schoolMateModels);
        init();
    }

    @Override
    public void deleteSchoolMateFailed() {

    }

    @Override
    public void deleteMemberSuccess() {

    }

    @Override
    public void deleteMemberFailed() {

    }

    @Override
    public void showDialog(String message) {
        dialog = CustomProgressDialog.show(UserDetailActivity.this, message, false, null);
        dialog.show();
    }

    @Override
    public void hideDialog() {
        dialog.dismiss();
    }

    @Override
    public void showError(String errorMessage) {
        Toast.makeText(UserDetailActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}
