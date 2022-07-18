package com.zy.website.facade.enums;

import org.apache.commons.lang3.StringUtils;

public enum MenuTypeEnum implements BaseEnum{

    MENU_TOP("TOP","顶部菜单"),
    MENU_FILM("FILM_MENU","视频菜单");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    MenuTypeEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static MenuTypeEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (MenuTypeEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
