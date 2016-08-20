package com.yetland.myfriend.presenter;

import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.ActivityModel;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.view.SearchView;
import com.yetland.myfriend.view.activity.SearchActivity;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/25.
 */
public class SearchActivityPresenter implements BasePresenter {

    @Inject
    RestApi restApi;
    private SearchView searchView;
    private SearchActivity activity;
    private List<ActivityModel> activityModels;

    public SearchActivityPresenter(RestApi restApi, SearchView searchView, SearchActivity activity) {
        this.restApi = restApi;
        this.searchView = searchView;
        this.activity = activity;
    }

    public void searchActivity(String title) {
        loading();
        restApi.searchActivity(title)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Subscriber<ResponseModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        loadFailure();
                        String errorMessage = ErrorMessageFactory.creat(searchView.getContext(), new DefaultErrorBundle((Exception) e).getException());
                        searchView.showErrorMessage(errorMessage);
                    }

                    @Override
                    public void onNext(ResponseModel responseModel) {
                        if (responseModel != null && responseModel.getCode().equals(ResultCodeList.SEARCH_ACTIVITY_SUCCESS)) {
                            activityModels = responseModel.getContent().getActivityModels();
                            if (activityModels.size() == 0) {
                                noResult();
                            } else {
                                loadSuccess();
                                searchView.renderActivityModel(activityModels);
                            }
                        } else {
                            loadFailure();
                            searchView.showErrorMessage("加载失败...");
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
        searchView.showRetry();
        searchView.hideResult();
        searchView.hideLoading();
        searchView.hideNoResult();
    }

    @Override
    public void loadSuccess() {
        searchView.showResult();
        searchView.hideRetry();
        searchView.hideLoading();
        searchView.hideNoResult();
    }

    @Override
    public void loading() {
        searchView.showLoading();
        searchView.hideRetry();
        searchView.hideResult();
        searchView.hideNoResult();
    }

    public void noResult() {
        searchView.showNoResult();
        searchView.hideRetry();
        searchView.hideResult();
        searchView.hideLoading();
    }
}
