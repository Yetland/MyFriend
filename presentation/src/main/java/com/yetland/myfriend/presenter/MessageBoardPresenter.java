package com.yetland.myfriend.presenter;

import android.util.Log;

import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.MessageModel;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.view.MessageBoardView;
import com.yetland.myfriend.view.activity.MessageBoardActivity;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/27.
 */
public class MessageBoardPresenter implements BasePresenter {

    private static final String TAG = "MessageBoardPresenter";
    @Inject
    RestApi restApi;
    MessageBoardView messageBoardView;
    MessageBoardActivity activity;

    public MessageBoardPresenter(RestApi restApi, MessageBoardView messageBoardView, MessageBoardActivity activity) {
        this.restApi = restApi;
        this.messageBoardView = messageBoardView;
        this.activity = activity;
    }

    public void getMessageBoard(int toId) {
        loading();
        restApi.getMessageBoard(toId)
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
                        String errorMessage = ErrorMessageFactory.creat(messageBoardView.getContext(), errorBundle.getException());
                        messageBoardView.showErrorMessage(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel == null && responseModel.getCode() != ResultCodeList.GET_MESSAGE_BOARD_SUCCESS) {
                            loadFailure();
                            messageBoardView.showErrorMessage("加载失败...");
                        } else {
                            MessageModel messageModel = responseModel.getContent().getMessageModel();
                            Log.e(TAG, "onNext:" + messageModel.toString());
                            if (messageModel.getMessageBoard() == null || messageModel.getMessageBoard().size() == 0) {
                                noMessageBoardData();
                            } else {
                                messageBoardView.renderData(messageModel);
                                loadSuccess();
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
        messageBoardView.showRetry();
        messageBoardView.hideLoading();
        messageBoardView.hideNoResult();
        messageBoardView.hideResult();
    }

    @Override
    public void loadSuccess() {
        messageBoardView.hideLoading();
        messageBoardView.hideRetry();
        messageBoardView.hideNoResult();
        messageBoardView.showResult();
    }

    @Override
    public void loading() {
        messageBoardView.showLoading();
        messageBoardView.hideRetry();
        messageBoardView.hideNoResult();
        messageBoardView.hideResult();
    }

    public void noMessageBoardData() {
        messageBoardView.showNoResult();
        messageBoardView.hideRetry();
        messageBoardView.hideLoading();
        messageBoardView.hideResult();
    }
}
