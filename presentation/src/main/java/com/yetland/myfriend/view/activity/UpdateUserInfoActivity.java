package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.databinding.ActivityUpdateUserInfoBinding;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.UpdateUserInfoPresenter;
import com.yetland.myfriend.view.widget.CustomProgressDialog;
import com.yetland.myfriend.view.LoadView;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateUserInfoActivity extends BaseActivity implements LoadView {

    private static final String TAG = "UpdateUserInfoActivity";
    @Bind(R.id.nick)
    EditText mNick;
    @Bind(R.id.email)
    EditText mEmail;
    @Bind(R.id.sex)
    EditText mSex;
    @Bind(R.id.school_name)
    EditText mSchoolName;
    @Bind(R.id.graduate_year)
    EditText mGraduateYear;
    @Bind(R.id.class_name)
    EditText mClassName;
    @Bind(R.id.ensure)
    Button mEnsure;
    @Inject
    RestApi restApi;
    UserModel userModel;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.tv_title)
    TextView tvTitle;
    @Bind(R.id.sign)
    EditText mSign;
    private CustomProgressDialog dialog;
    private UpdateUserInfoPresenter presenter;
    private ActivityUpdateUserInfoBinding binding;
    private boolean isNewUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_update_user_info);

        ButterKnife.bind(this, binding.getRoot());

        toolbar.setTitle(" ");
        tvTitle.setText("修改信息");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        userModel = new UserModel();
        userModel = (UserModel) getIntent().getSerializableExtra("user");
        isNewUser = getIntent().getBooleanExtra("isNewUser", false);
        if (userModel != null) {
            Log.e(TAG, "onCreate:" + userModel.toString());
            presenter = new UpdateUserInfoPresenter(UpdateUserInfoActivity.this, restApi, this);
            binding.setUser(userModel);
        } else {
            Toast.makeText(UpdateUserInfoActivity.this, "Error", Toast.LENGTH_SHORT).show();
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

    @OnClick(R.id.ensure)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ensure:
                attemptUpdateUserInfo();
                break;
        }
    }

    private void attemptUpdateUserInfo() {

        String nick, email, sex, sign, schoolName, graduateYear, className;

        mNick.setError(null);
        mEmail.setError(null);
        mSex.setError(null);
        mSign.setError(null);
        mSchoolName.setError(null);
        mGraduateYear.setError(null);
        mClassName.setError(null);

        nick = mNick.getText().toString();
        email = mEmail.getText().toString();
        sex = mSex.getText().toString();
        sign = mSign.getText().toString();
        schoolName = mSchoolName.getText().toString();
        graduateYear = mGraduateYear.getText().toString();
        className = mClassName.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(nick)) {
            mNick.setError("昵称呢?");
            cancel = true;
            focusView = mNick;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            userModel.setNick(nick);
            userModel.setEmail(email);
            userModel.setSex(sex);
            userModel.setSign(sign);
            userModel.setSchoolName(schoolName);
            userModel.setGraduateYear(graduateYear);
            userModel.setClassName(className);
            Log.e(TAG, "attemptUpdateUserInfo:" + userModel.toString());
            if (userModel != null)
                presenter.updateUserInfo(userModel);
        }
    }

    @Override
    public void showLoading() {
        dialog = CustomProgressDialog.show(UpdateUserInfoActivity.this, "修改中...", false, null);
    }

    @Override
    public void hideLoading() {
        dialog.dismiss();
    }

    @Override
    public void showRetry() {

    }

    @Override
    public void hideRetry() {

    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Toast.makeText(UpdateUserInfoActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return UpdateUserInfoActivity.this;
    }

    public void gotoMainActivity() {
        if (isNewUser) {
            startActivity(new Intent(UpdateUserInfoActivity.this, MainActivity.class));
        }
        setResult(ResultCodeList.UPDATE_SUCCESS);
        finish();
    }
}
