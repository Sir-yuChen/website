package com.zy.website.enums;

import org.apache.commons.lang3.StringUtils;

public enum UserStatusEnum implements BaseEnum{

    USER_STATE_NORMAL("1","正常"),
    USER_STATE_FREEZE("2","冻结"),
    USER_STATE_LOGOUT("0","已注销");


    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    UserStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static UserStatusEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (UserStatusEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
