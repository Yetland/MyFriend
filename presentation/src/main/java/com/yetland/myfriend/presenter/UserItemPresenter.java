package com.yetland.myfriend.presenter;

import android.util.Log;

import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.Content;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.view.ActivityDetailView;
import com.yetland.myfriend.view.activity.ActivityDetailActivity;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/21.
 */
public class UserItemPresenter implements BasePresenter {

    private static final String TAG = "UserItemPresenter";
    @Inject
    RestApi restApi;
    private ActivityDetailActivity activityDetailActivity;
    private ActivityDetailView detailView;

    public UserItemPresenter(RestApi restApi, ActivityDetailActivity activityDetailActivity, ActivityDetailView detailView) {
        this.restApi = restApi;
        this.activityDetailActivity = activityDetailActivity;
        this.detailView = detailView;
    }

    public void getActivityMember(int activityId) {
        loading();
        restApi.getActivityMember(activityId)
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
                        String errorMessage = ErrorMessageFactory.creat(activityDetailActivity, errorBundle.getException());
                        detailView.showErrorMessage(errorMessage);
                    }

                    // TODO
                    @Override
                    public void onNext(ResponseModel responseModel) {

                        if (responseModel != null) {
                            Log.e(TAG, "onNext:" + responseModel.toString());
                            if (responseModel.getCode().equals(ResultCodeList.GET_ACTIVITY_MEMBER_SUCCESS)) {
                                Content content = responseModel.getContent();
                                if (content.getActivityMembers() == null || content.getActivityMembers().size() == 0) {
                                    Log.e(TAG, "onNext:" + "活动不存在");
                                    loadFailure();
                                    detailView.showErrorMessage("活动已删除");
                                } else {
                                    Log.e(TAG, "onNext:" + "loadSuccess");
                                    loadSuccess();
                                    detailView.renderData(content.getActivityMembers());
                                }
                            }
                        } else {
                            loadFailure();
                            detailView.showErrorMessage("加载失败...");
                        }
                    }
                });
    }

    public void joinActivity(int activityId, int creatorId, int memberId) {

        detailView.showDialog("正在加入...");
        restApi.joinActivity(activityId, creatorId, memberId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.hideDialog();
                        String errorMessage = ErrorMessageFactory.creat(detailView.getContext(),
                                new DefaultErrorBundle((Exception) e).getException());
                        detailView.showToast(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        detailView.hideDialog();
                        if (responseModel != null && responseModel.getCode().equals(ResultCodeList.JOIN_ACTIVITY_SUCCESS)) {
                            detailView.joinActivitySuccess(responseModel.getContent().getActivityMembers());
                        } else if (responseModel != null && responseModel.getCode().equals(ResultCodeList.NO_ACTIVITY)) {
                            loadFailure();
                            detailView.showErrorMessage(responseModel.getMsg());
                        }
                    }
                });
    }

    public void quitActivity(int activityId, int creatorId, int memberId) {

        detailView.showDialog("正在退出...");
        restApi.quitActivity(activityId, creatorId, memberId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.hideDialog();
                        String errorMessage = ErrorMessageFactory.creat(detailView.getContext(),
                                new DefaultErrorBundle((Exception) e).getException());
                        detailView.showToast(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        detailView.hideDialog();
                        if (responseModel != null && responseModel.getCode().equals(ResultCodeList.QUIT_ACTIVITY_SUCCESS)) {
                            detailView.quitActivitySuccess(responseModel.getContent().getActivityMembers());
                        } else if (responseModel != null && responseModel.getCode().equals(ResultCodeList.NO_ACTIVITY)) {
                            loadFailure();
                            detailView.showErrorMessage(responseModel.getMsg());
                        }
                    }
                });
    }

    public void dismissActivity(ActivityModel activityModel) {
        detailView.showDialog("正在解散...");
        restApi.deleteActivity(activityModel)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        detailView.hideDialog();
                        String errorMessage = ErrorMessageFactory.creat(detailView.getContext(),
                                new DefaultErrorBundle((Exception) e).getException());
                        detailView.showToast(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        detailView.hideDialog();
                        if (responseModel != null && responseModel.getCode().equals(ResultCodeList.DELETE_ACTIVITY_SUCCESS)) {
                            detailView.dismissActivitySuccess();
                        } else {
                            loadFailure();
                            detailView.showErrorMessage("解散失败...");
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
        detailView = null;
    }

    @Override
    public void loadFailure() {
        detailView.hideLoading();
        detailView.hideRecyclerView();
        detailView.showRetry();
    }

    @Override
    public void loadSuccess() {
        detailView.hideLoading();
        detailView.hideRetry();
        detailView.showRecyclerView();
    }

    @Override
    public void loading() {
        detailView.showLoading();
        detailView.hideRetry();
        detailView.hideRecyclerView();
    }
}
