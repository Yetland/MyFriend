package com.yetland.myfriend.presenter;

import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.view.UserDetailView;
import com.yetland.myfriend.view.activity.UserDetailActivity;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/26.
 */
public class UserDetailPresenter implements BasePresenter {

    @Inject
    RestApi restApi;
    UserDetailActivity activity;
    UserDetailView detailView;

    public UserDetailPresenter(RestApi restApi, UserDetailActivity activity, UserDetailView detailView) {
        this.restApi = restApi;
        this.activity = activity;
        this.detailView = detailView;
    }

    public void sendSchoolMateInvitation(int fromId, int toId) {
        detailView.showDialog("正在发送邀请...");
        restApi.sendSchoolMateInvitation(fromId, toId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorMessage = ErrorMessageFactory.creat(activity, new DefaultErrorBundle((Exception) e).getException());
                        detailView.hideDialog();
                        detailView.sendInvitationFailed();
                        detailView.showError(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        detailView.hideDialog();
                        if (responseModel != null) {
                            if (responseModel.getCode().equals(ResultCodeList.SEND_ADD_SCHOOL_MATE_INVITATION_SUCCESS)) {
                                detailView.showError("发送成功");
                                detailView.sendInvitationSuccess();
                            }
                        } else {
                            detailView.showError("发送失败");
                        }
                    }
                });
    }

    public void deleteSchoolMate(int fromId, int toId) {
        detailView.showDialog("正在删除校友");
        restApi.deleteSchoolMate(fromId, toId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorMessage = ErrorMessageFactory.creat(activity, new DefaultErrorBundle((Exception) e).getException());
                        detailView.hideDialog();
                        detailView.deleteMemberFailed();
                        detailView.showError(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        detailView.hideDialog();
                        if (responseModel != null) {
                            if (responseModel.getCode().equals(ResultCodeList.DELETA_SCHOOL_MATE_SUCCESS)) {
                                detailView.deleteSchoolMateSuccess();
                                detailView.showError("删除校友成功");
                            }
                        } else {
                            detailView.showError("删除失败");
                        }
                    }
                });
    }

    public void deleteActivityMember(int activityId, int creatorId, int memberId) {
        detailView.showDialog("正在删除成员");
        restApi.deleteActivityMember(activityId, creatorId, memberId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        String errorMessage = ErrorMessageFactory.creat(activity, new DefaultErrorBundle((Exception) e).getException());
                        detailView.hideDialog();
                        detailView.deleteSchoolMateFailed();
                        detailView.showError(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        detailView.hideDialog();
                        if (responseModel != null) {
                            if (responseModel.getCode().equals(ResultCodeList.DELETE_ACTIVITY_MEMBER_SUCCESS)) {
                                detailView.deleteMemberSuccess();
                                detailView.showError("删除成员成功");
                            }
                        } else {
                            detailView.deleteSchoolMateFailed();
                            detailView.showError("删除失败");
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

    }

    @Override
    public void loadSuccess() {

    }

    @Override
    public void loading() {

    }
}
