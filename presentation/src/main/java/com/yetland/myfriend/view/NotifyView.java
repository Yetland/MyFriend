package com.yetland.myfriend.view;

import com.yetland.myfriend.model.SchoolMateInvitation;

import java.util.List;

/**
 * Created by yeliang on 2016/4/26.
 */
public interface NotifyView extends LoadView {
    void hideNoResult();

    void showNoResult();

    void showResult();

    void hideResult();

    void renderData(List<SchoolMateInvitation> schoolMateInvitations);

    void showToast(String message);

    void showDialog(String content);

    void hideDialog();

    void acceptInvitationSuccess(int position);
}
