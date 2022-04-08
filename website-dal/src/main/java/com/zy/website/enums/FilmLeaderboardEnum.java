package com.zy.website.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Administrator
 */

public enum FilmLeaderboardEnum implements BaseEnum {

    //所有榜单数据
    LEADERBOARD_ALL_NOTICE("FILM_ALL_NOTICE", null,"ALL", 0),
    LEADERBOARD_FILM_NOTICE("FILM_NOTICE", "FILM","电影总榜", 3),
    LEADERBOARD_VARIETY_NOTICE("VARIETY_NOTICE","VARIETY", "综艺总榜", 5),
    LEADERBOARD_CARTOON_NOTICE("CARTOON_NOTICE", "CARTOON","动漫总榜", 7),
    LEADERBOARD_USDRAMA_NOTICE("USDRAMA_NOTICE", "USDRAMA","美剧总榜", 6),
    LEADERBOARD_HIT_NOTICE("HIT_NOTICE", null,"热播榜", 1),
    LEADERBOARD_SCORE_NOTICE("SCORE_NOTICE", null,"高分榜", 2),
    LEADERBOARD_EPISODE_NOTICE("EPISODE_NOTICE", "EPISODE","剧集总榜", 4);

    private String code;
    private String typeCode;//为FilmGenreEnum code值，视频分类
    private String desc;
    private Integer order;

    public String getTypeCode() {
        return typeCode;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getOrder() {
        return order;
    }

    FilmLeaderboardEnum(String code,String typeCode,String desc, Integer order) {
        this.code = code;
        this.desc = desc;
        this.order = order;
        this.typeCode = typeCode;
    }

    public static FilmLeaderboardEnum getByCode(String code) {
        if (StringUtils.isNotBlank(code)) {
            for (FilmLeaderboardEnum e : values()) {
                if (e.code.equals(code)) {
                    return e;
                }
            }
        }
        return null;
    }

}
