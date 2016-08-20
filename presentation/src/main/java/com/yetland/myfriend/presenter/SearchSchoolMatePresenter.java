package com.yetland.myfriend.presenter;

import com.yetland.myfriend.core.data.ResultCodeList;
import com.yetland.myfriend.core.api.RestApi;
import com.yetland.myfriend.core.exception.DefaultErrorBundle;
import com.yetland.myfriend.core.exception.ErrorMessageFactory;
import com.yetland.myfriend.model.ResponseModel;
import com.yetland.myfriend.model.SearchUserModel;
import com.yetland.myfriend.view.SearchView;
import com.yetland.myfriend.view.activity.SearchSchoolMateActivity;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yeliang on 2016/4/25.
 */
public class SearchSchoolMatePresenter implements BasePresenter {

    @Inject
    RestApi restApi;
    private SearchView searchView;
    private SearchSchoolMateActivity activity;
    private List<SearchUserModel> searchUserModelList;

    public SearchSchoolMatePresenter(RestApi restApi, SearchView searchView, SearchSchoolMateActivity activity) {
        this.restApi = restApi;
        this.searchView = searchView;
        this.activity = activity;
    }

    public void searchSchoolMate(String nick) {
        loading();
        restApi.searchSchoolMate(nick)
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
                        if (responseModel != null && responseModel.getCode().equals(ResultCodeList.SEARCH_SCHOOL_MATE_SUCCESS)) {
                            searchUserModelList = responseModel.getContent().getSearchUserModels();
                            if (searchUserModelList.size() == 0) {
                                noResult();
                            } else {
                                loadSuccess();
                                searchView.renderSearchUserModel(searchUserModelList);
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
