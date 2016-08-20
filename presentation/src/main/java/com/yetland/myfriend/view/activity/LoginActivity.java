package com.yetland.myfriend.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.yetland.myfriend.R;
import com.yetland.myfriend.core.view.activity.BaseActivity;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.presenter.LoginPresenter;
import com.yetland.myfriend.view.widget.CustomProgressDialog;
import com.yetland.myfriend.view.LoginView;
import com.yetland.myfriend.core.dagger2.component.AppComponent;
import com.yetland.myfriend.core.dagger2.component.DaggerApiComponent;
import com.yetland.myfriend.core.module.ApiModule;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends BaseActivity implements LoginView {

    private static final String TAG = "LoginActivity";
    @Bind(R.id.phoneNumber)
    AutoCompleteTextView mPhoneNumber;
    @Bind(R.id.password)
    EditText mPassword;
    // UI references.
    @Bind(R.id.email_sign_in_button)
    Button mEmailSignInButton;
    @Inject
    RestApi restApi;
    CustomProgressDialog dialog;
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        setTitle("登录/注册");
        presenter = new LoginPresenter(restApi, LoginActivity.this, this);
        // Set up the login form.
        mPassword.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == R.id.login || id == EditorInfo.IME_NULL) {
                LoginActivity.this.attemptLogin();
                return true;
            }
            return false;
        });
    }

    @Override
    protected void setupActivityComponent(AppComponent appComponent) {
        DaggerApiComponent.builder()
                .apiModule(new ApiModule(this))
                .appComponent(appComponent)
                .build()
                .inject(this);
    }

    private void attemptLogin() {

        // Reset errors.
        mPhoneNumber.setError(null);
        mPassword.setError(null);

        // Store values at the time of the login attempt.
        String phoneNumber = mPhoneNumber.getText().toString();
        String password = mPassword.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPassword.setError(getString(R.string.error_field_required));
            focusView = mPassword;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            focusView = mPassword;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(phoneNumber)) {
            mPhoneNumber.setError(getString(R.string.error_field_required));
            focusView = mPhoneNumber;
            cancel = true;
        } else if (!isPhoneNumberValid(phoneNumber)) {
            mPhoneNumber.setError(getString(R.string.error_invalid_phone_number));
            focusView = mPhoneNumber;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            presenter.loginOrRegister(phoneNumber, password);
        }
    }

    private boolean isPhoneNumberValid(String phoneNumber) {
        return !phoneNumber.contains(" ");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4 && !password.contains(" ");
    }

    @OnClick(R.id.email_sign_in_button)
    public void onClick() {
        attemptLogin();
    }

    @Override
    public void gotoMainActivity() {
        Log.e(TAG, "gotoMainActivity:");
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    @Override
    public void gotoRegisterActivity(UserModel userModel) {

        Log.e(TAG, "gotoRegisterActivity:");
        Intent intent = new Intent(LoginActivity.this, UpdateUserInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userModel);
        intent.putExtras(bundle);
        intent.putExtra("isNewUser", true);
        startActivity(intent);
        finish();
    }

    @Override
    public void showLoading() {
        dialog = CustomProgressDialog.show(getContext(), "加载中...", false, null);
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
        Snackbar.make(mEmailSignInButton, errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public Context getContext() {
        return LoginActivity.this;
    }
}

