package com.zy.website.enums;

import org.apache.commons.lang3.StringUtils;

public enum FilmLeaderboardEnum implements BaseEnum {

    LEADERBOARD_ALL_NOTICE("FILM_ALL_NOTICE", "ALL",0),
    LEADERBOARD_FILM_NOTICE("FILM_NOTICE", "电影总榜",3),
    LEADERBOARD_VARIETY_NOTICE("VARIETY_NOTICE", "综艺总榜",4),
    LEADERBOARD_CARTOON_NOTICE("CARTOON_NOTICE", "动漫总榜",6),
    LEADERBOARD_USDRAMA_NOTICE("USDRAMA_NOTICE", "美剧总榜",5),
    LEADERBOARD_HIT_NOTICE("HIT_NOTICE", "热播榜",1),
    LEADERBOARD_SCORE_NOTICE("SCORE_NOTICE", "高分榜",2),
    LEADERBOARD_EPISODE_NOTICE("EPISODE_NOTICE", "剧集总榜",3);

    private String code;
    private String desc;
    private Integer order;

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }

    public Integer getOrder() {
        return order;
    }

    FilmLeaderboardEnum(String code, String desc,Integer order) {
        this.code = code;
        this.desc = desc;
        this.order = order;
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
