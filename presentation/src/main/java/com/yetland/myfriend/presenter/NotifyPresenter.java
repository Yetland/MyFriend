package com.yetland.myfriend.presenter;

import android.util.Log;

import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.SchoolMateInvitation;
import com.yetland.myfriend.model.SchoolMateModel;
import com.yetland.myfriend.util.SharedPreferenceUtil;
import com.yetland.myfriend.view.NotifyView;
import com.yetland.myfriend.view.activity.NotifyActivity;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/26.
 */
public class NotifyPresenter implements BasePresenter {

    private static final String TAG = "NotifyPresenter";
    @Inject
    RestApi restApi;
    private NotifyView notifyView;
    private List<SchoolMateInvitation> schoolMateInvitations;
    private List<SchoolMateModel> schoolMateModels;

    public NotifyPresenter(RestApi restApi, NotifyView notifyView, NotifyActivity activity) {
        this.restApi = restApi;
        this.notifyView = notifyView;
    }

    public void getSchoolMateInvitation(int toId) {
        loading();
        restApi.getNotify(toId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                        loadFailure();
                        ErrorBundle errorBundle = new DefaultErrorBundle((Exception) e);
                        String errorMessage = ErrorMessageFactory.creat(notifyView.getContext(), errorBundle.getException());
                        notifyView.showErrorMessage(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel == null && responseModel.getCode() != ResultCodeList.GET_SCHOOL_MATE_INVITATION_SUCCESS) {
                            loadFailure();
                            notifyView.showErrorMessage("加载失败...");
                        } else {
                            schoolMateInvitations = responseModel.getContent().getSchoolMateInvitations();
                            Log.e(TAG, "onNext:" + schoolMateInvitations.toString());
                            if (schoolMateInvitations == null) {
                                loadFailure();
                            } else if (schoolMateInvitations.size() == 0) {
                                noNotifyData();
                            } else {
                                notifyView.renderData(schoolMateInvitations);
                                loadSuccess();
                            }
                        }
                    }
                });
    }

    public void acceptSchoolMateInvitation(int fromId, int toId,int position) {
        notifyView.showDialog("正在加载...");
        restApi.acceptSchoolMateInvitation(fromId, toId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        notifyView.hideDialog();
                        String errorMessage = ErrorMessageFactory.creat(notifyView.getContext(),
                                new DefaultErrorBundle((Exception) e).getException());
                        notifyView.showErrorMessage(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        notifyView.hideDialog();

                        if (responseModel != null && responseModel.getCode().equals(ResultCodeList.ADD_SCHOOL_MATE_SUCCESS)) {
                            schoolMateModels = responseModel.getContent().getSchoolMateModels();
                            if (schoolMateModels != null && schoolMateModels.size() != 0) {
                                // 存入内存中
                                SharedPreferenceUtil.saveSchoolMateList(notifyView.getContext(),schoolMateModels);
                                notifyView.acceptInvitationSuccess(position);
                                notifyView.showToast("同意成功");
                            }
                        } else {
                            loadFailure();
                            notifyView.showErrorMessage("加载失败");
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
        notifyView.showRetry();
        notifyView.hideLoading();
        notifyView.hideNoResult();
        notifyView.hideResult();
    }

    @Override
    public void loadSuccess() {
        notifyView.hideLoading();
        notifyView.hideRetry();
        notifyView.hideNoResult();
        notifyView.showResult();
    }

    @Override
    public void loading() {
        notifyView.showLoading();
        notifyView.hideRetry();
        notifyView.hideNoResult();
        notifyView.hideResult();
    }

    public void noNotifyData() {
        notifyView.showNoResult();
        notifyView.hideRetry();
        notifyView.hideLoading();
        notifyView.hideResult();
    }
}
