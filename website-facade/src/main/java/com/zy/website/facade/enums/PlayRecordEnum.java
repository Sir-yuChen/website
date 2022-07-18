package com.zy.website.facade.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @param $
 * @author zhangyu
 * @description $
 * @date $ $
 * @return $
 * @since
 **/
public enum PlayRecordEnum implements BaseEnum{

    //
    PLAY_RECORD_NORMAL("1","正常"),
    PLAY_RECORD_DEL("0","已删除");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    PlayRecordEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static PlayRecordEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (PlayRecordEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
