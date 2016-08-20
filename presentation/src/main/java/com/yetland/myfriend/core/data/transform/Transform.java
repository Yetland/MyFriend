package com.yetland.myfriend.core.data.transform;

import com.yetland.myfriend.model.ActivityMember;
import com.yetland.myfriend.model.SchoolMateModel;
import com.yetland.myfriend.model.SearchUserModel;
import com.yetland.myfriend.model.UserModel;

/**
 * Created by yeliang on 2016/4/25.
 */
public class Transform {

    public static SchoolMateModel ActivityMemberToSchoolMate(ActivityMember activityMember) {
        SchoolMateModel schoolMateModel = new SchoolMateModel();
        schoolMateModel.setToId(activityMember.getMemberId());
        schoolMateModel.setNick(activityMember.getNick());
        schoolMateModel.setEmail(activityMember.getEmail());
        schoolMateModel.setSex(activityMember.getSex());
        schoolMateModel.setSign(activityMember.getSign());

        schoolMateModel.setSchoolName(activityMember.getSchoolName());
        schoolMateModel.setClassName(activityMember.getClassName());
        schoolMateModel.setGraduateYear(activityMember.getGraduateYear());

        return schoolMateModel;
    }

    public static SchoolMateModel SearchUserToSchoolMate(SearchUserModel searchUserModel) {

        SchoolMateModel schoolMateModel = new SchoolMateModel();
        schoolMateModel.setToId(searchUserModel.getId());
        schoolMateModel.setNick(searchUserModel.getNick());
        schoolMateModel.setEmail(searchUserModel.getEmail());
        schoolMateModel.setSex(searchUserModel.getSex());
        schoolMateModel.setSign(searchUserModel.getSign());

        schoolMateModel.setSchoolName(searchUserModel.getSchoolName());
        schoolMateModel.setGraduateYear(searchUserModel.getGraduateYear());
        schoolMateModel.setClassName(searchUserModel.getClassName());

        return schoolMateModel;
    }

    public static SchoolMateModel UserToSchoolMate(UserModel userModel) {
        SchoolMateModel schoolMateModel = new SchoolMateModel();
        schoolMateModel.setToId(userModel.getId());
        schoolMateModel.setNick(userModel.getNick());
        schoolMateModel.setEmail(userModel.getEmail());
        schoolMateModel.setSex(userModel.getSex());
        schoolMateModel.setSign(userModel.getSign());

        schoolMateModel.setSchoolName(userModel.getSchoolName());
        schoolMateModel.setGraduateYear(userModel.getGraduateYear());
        schoolMateModel.setClassName(userModel.getClassName());

        return schoolMateModel;
    }
}
