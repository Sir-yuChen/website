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
    SEND_MAIL_HTML_CONTENT("10009", "邮件模板内容不能为空"),
    SEND_MAIL_HTML_IMGURL("10010", "请上传图片！"),
    SEND_MAIL_TYPE("10011", "请选择邮件类型！"),
    NO_TERM_DATAS("10011", "没有符合条件的数据！"),
    PERMISSION_FLAG_REPEAT("10012", "权限标识不能重复！"),
    ROLE_NAME_REPEAT("10013", "角色名已存在！"),
    ROLE_UPDATE_STATUS_ERROR("10014", "当前账户不具备该角色,操作失败！"),
    UPLOAD_FILE_NULL_ERROR("10015", "上传文件不能为空！"),
    UPLOAD_FILE_PATH_ERROR("10016", "上传文件路径异常！"),
    //图床异常处理
    IMAGE_EXIST_ERROR("10017", "服务器未接收到上传资源"),
    IMAGE_UPLOAD_ERROR("10018", "上传Gitee图床失败"),
    DEL_FILE_FAILED("10020", "文件删除失败"),
    URL_PARSE_FAILED("10021", "Gitee图片url无法解析"),
    FILE_NAME_REPEAT("10022", "文件名已存在"),
    FILE_SUFFIX_ERROR("10023", "模板文件暂时只支持【xls】"),
    TEMPLATE_NO_ERROR("10024", "当前模板不被支持"),
    TEMPLATE_NO_USE_ERROR("10025", "当前模板暂不可用"),
    ACTIVITY_SPIKE_GOODS("10026", "秒杀失败,请重新再试"),
    ACTIVITY_NO_START("10027", "活动未开始,请稍后"),
    ACTIVITY_END_START("10028", "活动已结束"),
    ACTIVITY_SPIKE_GOODS_SUCCESS("10029", "秒杀成功"),

    /**
     * *********************************************************
     * XXXXXXXXX相关异常，code的定义范围为【10600~10699】
     * *********************************************************
     */
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