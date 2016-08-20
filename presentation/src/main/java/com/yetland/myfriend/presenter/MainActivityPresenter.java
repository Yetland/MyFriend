package com.yetland.myfriend.presenter;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.core.exception.NetworkConnectionException;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.SchoolMateModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.util.CustomUtils;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.MainView;
import com.yetland.myfriend.view.activity.MainActivity;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/19.
 */
public class MainActivityPresenter implements BasePresenter {

    private static final String TAG = "MainActivityPresenter";
    @Inject
    RestApi restApi;
    private MainView mainView;
    private MainActivity mainActivity;

    public MainActivityPresenter(RestApi restApi, MainView mainView, MainActivity mainActivity) {
        Log.e(TAG, "MainActivityPresenter:");
        this.restApi = restApi;
        this.mainView = mainView;
        this.mainActivity = mainActivity;
    }

    public void getMainData(UserModel userModel) {
        Log.e(TAG, "getMainData:");
        loading();
        Observable<ResponseModel> observable = restApi.getMain(userModel.getId(),userModel.getPhoneNumber(),userModel.getPassword());
        observable.create(subscriber -> {
            if (isThereInternetConnection())
                Log.e(TAG, "getMainData:" + "NoNetWork");
           subscriber.onError(new NetworkConnectionException());
        });
       observable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                               @Override
                               public void onCompleted() {
                                   Log.e(TAG, "onCompleted:");
                               }

                               @Override
                               public void onError(Throwable e) {
                                   Log.e(TAG, "onError:");
                                   loadFailure();
                                   ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                                   String errorMsg = ErrorMessageFactory.creat(mainActivity, errorBundle.getException());
                                   mainView.showErrorMessage(errorMsg);
                               }

                               @Override
                               public void onNext(ResponseModel responseModel) {
                                   Log.e(TAG, "onNext:" + responseModel.toString());
                                   if (responseModel != null) {
                                       if (responseModel.getCode().equals(ResultCodeList.GET_MAIN_RECOMMEND_SUCCESS)) {
                                           // 保存User的信息
                                           UserModel userModel = responseModel.getContent().getUserModel();
                                           List<SchoolMateModel> schoolMateModels = responseModel.getContent().getSchoolMateModels();

                                           Log.e(TAG, "onNext:" + userModel.toString());
                                           Log.e(TAG, "onNext:" + schoolMateModels.toString());
                                           // 保存好友列表的信息
                                           SharedPreferenceUtil.saveObject(mainActivity, CustomUtils.key.USER_INFO, userModel, CustomUtils.fileName.USER);
                                           SharedPreferenceUtil.saveSchoolMateList(mainActivity, schoolMateModels);

                                           loadSuccess();
                                           mainView.renderData(responseModel);
                                       } else if (responseModel.getCode().equals(ResultCodeList.PASSWORD_ERROR)) {
                                           mainView.passwordError();
                                       } else if (responseModel.getCode().equals(ResultCodeList.GET_MAIN_RECOMMEND_FAILED)) {
                                           loadFailure();
                                           mainView.showErrorMessage(responseModel.getMsg());
                                       }
                                   } else {
                                       loadFailure();
                                       mainView.showErrorMessage("未知错误");
                                   }
                               }
                           }
                );
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;
        ConnectivityManager connectivityManager =
                (ConnectivityManager) mainView.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        Log.e(TAG, "isThereInternetConnection:" + isConnected);
        return isConnected;
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
        mainView.hideMainView();
        mainView.hideLoading();
        mainView.showRetry();
    }

    @Override
    public void loadSuccess() {
        mainView.hideLoading();
        mainView.hideRetry();
        mainView.showMainView();
    }

    @Override
    public void loading() {
        mainView.showLoading();
        mainView.hideRetry();
    }
}
