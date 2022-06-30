package com.zy.website.facade.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 * @date 2022/2/27 21:17
 **/

public enum FilmGenreEnum implements BaseEnum{

    GENRE_FILM("FILM","电影"),
    GENRE_VARIETY("VARIETY","综艺"),
    GENRE_CARTOON("CARTOON","动漫"),
    GENRE_USDRAMA("USDRAMA","美剧"),
    GENRE_MICROFILM("MICROFILM","微电影"),
    GENRE_EPISODE("EPISODE","剧集");

    private String code;
    private String desc;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    FilmGenreEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static FilmGenreEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (FilmGenreEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
