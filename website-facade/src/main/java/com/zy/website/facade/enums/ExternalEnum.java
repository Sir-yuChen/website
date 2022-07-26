package com.zy.website.facade.enums;

import org.apache.commons.lang3.StringUtils;


public enum ExternalEnum implements BaseEnum{

    //
    WMDB_TV("WMDB_TV","视频模糊查"),
    WMDB_TV_250("WMDB_TV_250","视频250"),
    FRIEND_LINKS("FRIEND_LINKS","友链");

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
