package com.yetland.myfriend.core.data;

/**
 * Created by yeliang on 2016/4/15.
 */
public class ResultCodeList {

    public static final String EMPTY_INPUT = "1001";
    public static final String PARSE_FAILED = "4001";
    public static final String NO_ACTIVITY="4002";

    public static final String PASSWORD_ERROR = "1002";
    public static final String LOGIN_SUCCESS = "1003";
    public static final String REGISTER_SUCCESS = "1004";
    public static final String UPDATE_USER_INFO_SUCCESS = "2001";
    public static final String UPDATE_USER_INFO_FAILED = "2002";
    public static final String SEARCH_SCHOOL_MATE_SUCCESS = "3001";
    public static final String SEARCH_SCHOOL_MATE_FAILED = "3002";
    public static final String SEND_ADD_SCHOOL_MATE_INVITATION_SUCCESS = "3003";
    public static final String SEND_ADD_SCHOOL_MATE_INVITATION_FAILED = "3004";
    public static final String GET_SCHOOL_MATE_INVITATION_SUCCESS = "3005";
    public static final String GET_SCHOOL_MATE_INVITATION_FAILED = "3006";
    public static final String ADD_SCHOOL_MATE_SUCCESS = "3007";
    public static final String ADD_SCHOOL_MATE_FAILED = "3008";
    public static final String GET_SCHOOL_MATE_SUCCESS = "3009";
    public static final String GET_SCHOOL_MATE_FAILED = "3010";
    public static final String DELETA_SCHOOL_MATE_SUCCESS = "3011";
    public static final String DELETE_SCHOOL_MATE_FAILED = "3012";
    public static final String GET_MESSAGE_BOARD_SUCCESS = "5001";
    public static final String GET_MESSAGE_BOARD_FAILED = "5002";
    public static final String DELETE_MESSAGE_BOARD_SUCCESS = "5003";
    public static final String DELETE_MESSAGE_BOARD_FAILED = "5004";
    public static final String REPLY_MESSAGE_BOARD_SUCCESS = "5005";
    public static final String REPLY_MESSAGE_BOARD_FAILED = "5006";
    public static final String LEAVE_MESSAGE_BOARD_SUCCESS = "5007";
    public static final String LAEVE_MESSAGE_BOARD_FAILED = "5008";
    public static final String GET_MAIN_RECOMMEND_SUCCESS = "5001";
    public static final String GET_MAIN_RECOMMEND_FAILED = "6002";
    public static final String GET_ACTIVITY_SUCCESS = "6003";
    public static final String GET_ACTIVITY_FAILED = "6004";
    public static final String GET_ACTIVITY_MEMBER_SUCCESS = "6005";
    public static final String GET_ACTIVITY_MEMBER_FAILED = "6006";
    public static final String CREATE_ACTIVITY_SUCCESS = "6007";
    public static final int CREATE_SUCCESS = 6007;
    public static final int DELETE_SUCCESS = 6008;
    public static final int UPDATE_SUCCESS = 6009;
    public static final String CREATE_ACTIVITY_FAILED = "6008";
    public static final String JOIN_ACTIVITY_SUCCESS = "6009";
    public static final String JOIN_ACTIVITY_FAILED = "6010";
    public static final String DELETE_ACTIVITY_MEMBER_SUCCESS = "6011";
    public static final String DELETE_ACTIVITY_MEMBER_FAILED= "6012";
    public static final String SEARCH_ACTIVITY_SUCCESS= "6013";
    public static final String SEARCH_ACTIVITY_FAILED= "6014";
    public static final String QUIT_ACTIVITY_SUCCESS = "6015";
    public static final String QUIT_ACTIVITY_FAILED = "6016";
    public static final String DISMISS_ACTIVITY_SUCCESS = "6017";
    public static final String DISMISS_ACTIVITY_FAILED = "6018";
    public static final String DELETE_ACTIVITY_SUCCESS= "6019";
    public static final String DELETE_ACTIVITY_FAILED= "6020";
    public enum ACTIVITY_MEMBER_TYPE {
        CREATOR,
        MEMBER,
        NOT_MEMBER,
        CREATOR_IS_ME,
        NONE
    }

    public enum loadCode {
        LOADING,
        LOAD_SUCCESS,
        LOAD_FAILURE
    }

    public enum DialogType {
        EDITTEXT,
        TEXTVIEW,
        SCORE
    }
}
