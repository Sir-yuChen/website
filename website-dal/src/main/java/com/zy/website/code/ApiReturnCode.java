package com.zy.website.code;


import org.apache.commons.lang3.StringUtils;

/**
 * 前台返回值的枚举类，定义返回的code及message
 */
public enum ApiReturnCode {
    //操作成功状态码
    SUCCESSFUL("200", "成功"),//【前端使用】
    NOT_AUTHORIZED("401", "用户未登录"),//【前端使用】
    LONGING_LOSE("001", "登录已失效"),//【前端使用】
    HTTP_ERROR("000", "系统异常"),
    NO_PERMISSION("0000", "权限不足"),
    //方法参数错误状态码
    USER_REGISTER_ERROR("10000", "用户注册异常"),
    USERNAME_EXISTED_ERROR("10001", "用户名已存在"),
    USERNAME_LOGIN_ERROR("10002", "登录失败"),
    USERNAME_LOGINED("10003", "当前用户已登录，请不要重复登录"),
    PLEASE_AGAIN_LATER("10004", "操作频繁，请稍后再试！"),
    VERIFYCODE_LOSE("10005", "验证码已失效或不存在"),
    USER_STATE_FREEZE("10006", "账户冻结,请联系管理员！"),
    USER_STATE_LOGOUT("10007", "账户不存在,请注册后在登录！"),
    USER_NEW_PWD_SUCCESS("10008", "重置密码成功,请使用新密码登录！"),

    NO_PLAY_RECORD_DATA("10009", "无播放记录"),



    /**
     * *********************************************************
     * XXXXXXXXX相关异常，code的定义范围为【10600~10699】
     * *********************************************************
     */
    NO_DATA("7777","没有满足条数的数据"),
    PARAMS_ERROR("9999", "参数错误"),
    RESOURCE_IS_NOT("6666", "资源不存在"),
    // 服务器内部错误
    UNKNOWN_ERROR("8888", "未知错误");


    private String code;
    private String message;

    private ApiReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ApiReturnCode getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (ApiReturnCode e : values()) {
                if (e.getCode().equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}