package com.zy.website.enums;

import org.apache.commons.lang3.StringUtils;


public enum ExternalEnum implements BaseEnum{

    WMDB_TV("WMDB_TV","视频模糊查第三方接口");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    ExternalEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static ExternalEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (ExternalEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
