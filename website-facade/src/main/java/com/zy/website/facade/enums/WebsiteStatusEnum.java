package com.zy.website.facade.enums;


import org.apache.commons.lang3.StringUtils;

public enum  WebsiteStatusEnum implements BaseEnum{

    //菜单状态
    STATUS_MENU_Y("Y","正常"),
    STATUS_MENU_N("N","不可以");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    WebsiteStatusEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static WebsiteStatusEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (WebsiteStatusEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
