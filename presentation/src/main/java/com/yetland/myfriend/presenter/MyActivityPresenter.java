package com.yetland.myfriend.presenter;

import android.content.Context;
import android.util.Log;

import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.UserModel;
import com.yetland.myfriend.view.MyActivityListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/21.
 */
public class MyActivityPresenter implements BasePresenter {

    private static final String TAG = "MyActivityPresenter";
    @Inject
    RestApi restApi;
    private List<ActivityModel> activityModels;
    private List<ActivityModel> myCreateActivity;
    private List<ActivityModel> myJoinedActivity;
    private MyActivityListView myActivityListView;
    private Context context;
    private String type;
    private UserModel userModel;

    public MyActivityPresenter(RestApi restApi, Context context, MyActivityListView myActivityListView, String type, UserModel userModel) {
        this.restApi = restApi;
        this.context = context;
        this.myActivityListView = myActivityListView;
        this.type = type;
        this.userModel = userModel;
        myCreateActivity = new ArrayList<>();
        myJoinedActivity = new ArrayList<>();
    }

    public void getMyActivity() {
        loading();
        restApi.getMyActivity(userModel.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {
//                        loadSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadFailure();
                        ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                        String errorMessage = ErrorMessageFactory.creat(context, errorBundle.getException());
                        myActivityListView.showErrorMessage(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel == null) {
                            loadFailure();
                        } else {
                            loadSuccess();
                            Log.e(TAG, "onNext:" + responseModel.toString());
                            activityModels = responseModel.getContent().getActivityModels();
                            for (ActivityModel activityModel : activityModels) {
                                if (activityModel.getCreatorId() == userModel.getId()) {
                                    myCreateActivity.add(activityModel);
                                } else {
                                    myJoinedActivity.add(activityModel);
                                }
                            }
                            if (type.equals("myCreateActivity")) {
                                Log.e(TAG, "onNext:" + myCreateActivity.toString());
                                if (myCreateActivity.size() == 0) {
                                    noResult();
                                } else {
                                    myActivityListView.renderData(myCreateActivity);
                                }
                            } else if (type.equals("myJoinedActivity")) {
                                Log.e(TAG, "onNext:" + myJoinedActivity.toString());
                                if (myJoinedActivity.size() == 0) {
                                    noResult();
                                } else {
                                    myActivityListView.renderData(myJoinedActivity);
                                }
                            }
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

    }

    @Override
    public void loadFailure() {
        myActivityListView.showRetry();
        myActivityListView.hideLoading();
        myActivityListView.hideResult();
        myActivityListView.hideNoResult();
    }

    @Override
    public void loadSuccess() {
        myActivityListView.showResult();
        myActivityListView.hideLoading();
        myActivityListView.hideRetry();
        myActivityListView.hideNoResult();
    }

    @Override
    public void loading() {
        myActivityListView.showLoading();
        myActivityListView.hideNoResult();
        myActivityListView.hideResult();
        myActivityListView.hideRetry();
    }

    public void noResult() {
        myActivityListView.showNoResult();
        myActivityListView.hideLoading();
        myActivityListView.hideRetry();
        myActivityListView.hideResult();
    }
}
