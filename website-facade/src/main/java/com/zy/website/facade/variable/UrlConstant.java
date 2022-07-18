package com.zy.website.facade.variable;

/**
 * @author Administrator
 * @date 2022/7/2 23:11
 **/

public class UrlConstant {

    public static final String REMOTE_RUL = "http://localhost:8091/";

    //登录
    public static final String LONG_URL = REMOTE_RUL + "/api/user/login";
    //退登
    public static final String LONG_OUT_URL = REMOTE_RUL + "/api/user/logout";
    //查用户信息
    public static final String QUERY_USER_INFO = REMOTE_RUL + "/api/v1/user/selectUserInfo";


}
