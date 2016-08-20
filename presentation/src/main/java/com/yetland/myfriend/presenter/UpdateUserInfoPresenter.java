package com.yetland.myfriend.presenter;

import android.util.Log;

import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.Content;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.LoadView;
import com.yetland.myfriend.view.activity.UpdateUserInfoActivity;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/18.
 */
public class UpdateUserInfoPresenter implements BasePresenter {

    private UpdateUserInfoActivity updateUserInfoActivity;
    private RestApi restApi;
    private LoadView loadView;

    private static final String TAG = "UpdateUserInfoPresenter";
    public UpdateUserInfoPresenter(UpdateUserInfoActivity updateUserInfoActivity, RestApi restApi, LoadView loadView) {
        this.updateUserInfoActivity = updateUserInfoActivity;
        this.loadView = loadView;
        this.restApi = restApi;
    }

    public void updateUserInfo(UserModel userModel) {
        loading();
        restApi.updateUserInfo(userModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                        String errorMsg = ErrorMessageFactory.creat(updateUserInfoActivity, errorBundle.getException());
                        loadFailure();
                        loadView.showErrorMessage(errorMsg);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel != null) {
                            Log.e(TAG, "onNext:" + responseModel.toString());
                            Content content = responseModel.getContent();

                            loadSuccess();
                            SharedPreferenceUtil.saveObject(updateUserInfoActivity, CustomUtils.key.USER_INFO,
                                    content.getUserModel(), CustomUtils.fileName.USER);
                            updateUserInfoActivity.gotoMainActivity();
                        }
                    }
                });
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        loadView = null;
    }

    @Override
    public void loadFailure() {
        loadView.hideLoading();
    }

    @Override
    public void loadSuccess() {
        loadView.hideLoading();
    }

    @Override
    public void loading() {
        loadView.showLoading();
    }
}
