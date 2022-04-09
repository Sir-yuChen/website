package com.zy.website.model.dto;

import lombok.Data;

/**
 * @author zhangyu
 * @since 2022-02-24
 */
@Data
public class FilmInfoDTO {

    private Integer id;

    /**
     * 唯一uid
     * @mock 5f968bfcee3680299115bbe6
     * @since v1.0
     */
    private String filmUid;

    /**
     * 状态 Y正常 N暂停 D删除
     * @mock Y
     * @since v1.0
     */
    private String filmStatus;

    /**
     * 名称
     * @mock 肖申克的救赎
     * @since v1.0
     */
    private String filmName;

    /**
     * 原名
     * @mock 肖申克的救赎
     * @since v1.0
     */
    private String filmOriginalName;

    /**
     * 别名
     * @mock 肖申克的救赎
     * @since v1.0
     */
    private String filmAlias;

    /**
     * 视频分类
     * @mock EPISODE(剧情)
     * @since v1.0
     */
    private String filmGenre;

    /**
     * 语言
     * @mock 英语
     * @since v1.0
     */
    private String filmLanguage;

    /**
     * 电影时长单位分钟
     * @mock 120
     * @since v1.0
     */
    private Integer filmDuration;

    /**
     * 编剧
     * @mock 1,5
     * @since v1.0
     */
    private String filmScenaristId;

    /**
     * 主演
     * @mock 2,3,4
     * @since v1.0
     */
    private String filmStarringId;

    /**
     * 发行地区
     * @mock 美国
     * @since v1.0
     */
    private String filmPublishCountry;

    /**
     * 发行时间
     */
    private String filmPublishTime;

    /**
     * 发行地区简称
     * @mock cn
     * @since v1.0
     */
    private String filmLang;

    /**
     * url
     * @mock
     * @since v1.0
     */
    private String filmUrl;

    /**
     * 电影封面
     * @mock https://wmdb.querydata.org/movie/poster/1603701754760-c50d8a.jpg
     * @since v1.0
     */
    private String filmPoster;

    /**
     * 卡片
     * @mock https://wmdb.querydata.org/movie/poster/1605355459683-5f968bfaee3680299115bb97.png
     * @since v1.0
     */
    private String filmShareimage;

    /**
     * 简介
     */
    private String filmDescription;

    /**
     * 创建时间
     */
    private String creactTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 导演
     * @mock  张艺谋
     * @since v1.0
     */
    private String filmDirector;

    /**
     * 视频播放次数
     * @mock 10000
     * @since v1.0
     */
    private Long filmPlayCount;

}
