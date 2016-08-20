package com.yetland.myfriend.presenter;

import android.util.Log;

import com.yetland.domain.bundle.app.interactor.LoginUserCase;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.core.executor.DefaultSubscriber;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.LoginView;
import com.yetland.myfriend.view.activity.LoginActivity;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/15.
 */
public class LoginPresenter implements BasePresenter {


    @Inject
    LoginUserCase loginUserCase;
    private static final String TAG = "LoginPresenter";
    private RestApi restApi;
    private LoginActivity loginActivity;
    private LoginView loginView;
    private UserModel userModel;

    public LoginPresenter(RestApi restApi, LoginActivity loginActivity, LoginView loginView) {
        this.restApi = restApi;
        this.loginActivity = loginActivity;
        this.loginView = loginView;
    }

    public void loginOrRegister(String phoneNumber, String password) {
        loading();
        restApi.login(phoneNumber, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "onCompleted:");
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadFailure();
                        ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                        String msg = ErrorMessageFactory.creat(loginActivity, errorBundle.getException());
                        loginView.showErrorMessage(msg);

                        Log.e(TAG, "onError:" + msg);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        Log.e(TAG, "onNext:" + responseModel.toString());

                        loadSuccess();
                        if (responseModel.getCode().equals(ResultCodeList.PASSWORD_ERROR)) {
                            loginView.showErrorMessage("用户名或密码错误");
                        } else {
                            userModel = responseModel.getContent().getUserModel();

                            SharedPreferenceUtil.saveObject(loginActivity,CustomUtils.key.USER_INFO,userModel, CustomUtils.fileName.USER);

                            Log.e(TAG, "onNext:Code : " + responseModel.getCode());
                            Log.e(TAG, "onNext:userModel : " + responseModel.getContent().getUserModel());
                            if (responseModel.getCode().equals(ResultCodeList.LOGIN_SUCCESS)) {
                                Log.e(TAG, "onNext:" + "gotoMainActivity");
                                loginView.gotoMainActivity();
                            } else if (responseModel.getCode().equals(ResultCodeList.REGISTER_SUCCESS)) {
                                Log.e(TAG, "onNext:" + "gotoRegisterActivity");
                                loginView.gotoRegisterActivity(userModel);
                            }
                        }
                    }
                });
    }


    public void login(String phoneNumber, String password){
        loginUserCase.execute(new DefaultSubscriber<ResponseModel>(){
            @Override
            public void onCompleted() {
                super.onCompleted();

            }

            @Override
            public void onNext(ResponseModel responseModel) {
                responseModel.toString();
                super.onNext(responseModel);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
        },phoneNumber,password);

    }
    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void loadFailure() {
        loginView.hideLoading();
    }

    @Override
    public void loadSuccess() {
       loginView.hideLoading();
    }

    @Override
    public void loading() {
        loginView.showLoading();
    }
}
