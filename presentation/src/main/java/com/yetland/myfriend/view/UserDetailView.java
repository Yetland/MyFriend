package com.yetland.myfriend.view;

/**
 * Created by yeliang on 2016/4/26.
 */
public interface UserDetailView {

    void sendInvitationSuccess();
    void sendInvitationFailed();
    void deleteSchoolMateSuccess();
    void deleteSchoolMateFailed();
    void deleteMemberSuccess();
    void deleteMemberFailed();

    void showDialog(String message);
    void hideDialog();

    void showError(String errorMessage);
}
