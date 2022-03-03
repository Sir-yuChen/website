package com.zy.website.enums;

import org.apache.commons.lang3.StringUtils;


public enum  FilmImageEnum implements BaseEnum{

    POSTER("POSTER","视频封面");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    FilmImageEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FilmImageEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (FilmImageEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
