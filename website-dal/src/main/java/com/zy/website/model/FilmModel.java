package com.zy.website.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_film")
public class FilmModel extends Model<FilmModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 唯一uid
     * @mock 5f968bfcee3680299115bbe6
     * @since v1.0
     */
    @TableField("film_uid")
    private String filmUid;

    /**
     * 状态 Y正常 N暂停 D删除
     * @mock Y
     * @since v1.0
     */
    @TableField("film_status")
    private String filmStatus;

    /**
     * 名称
     * @mock 肖申克的救赎
     * @since v1.0
     */
    @TableField("film_name")
    private String filmName;

    /**
     * 原名
     * @mock 肖申克的救赎
     * @since v1.0
     */
    @TableField("film_original_name")
    private String filmOriginalName;

    /**
     * 别名
     * @mock 肖申克的救赎
     * @since v1.0
     */
    @TableField("film_alias")
    private String filmAlias;

    /**
     * 类型
     * @mock 1,2
     * @since v1.0
     */
    @TableField("film_genre_id")
    private String filmGenreId;

    /**
     * 语言
     * @mock 英语
     * @since v1.0
     */
    @TableField("film_language")
    private String filmLanguage;

    /**
     * 电影时长单位分钟
     * @mock 120
     * @since v1.0
     */
    @TableField("film_duration")
    private Integer filmDuration;

    /**
     * 编剧
     * @mock 1,5
     * @since v1.0
     */
    @TableField("film_scenarist_id")
    private String filmScenaristId;

    /**
     * 主演
     * @mock 2,3,4
     * @since v1.0
     */
    @TableField("film_starring_id")
    private String filmStarringId;

    /**
     * 发行地区
     * @mock 美国
     * @since v1.0
     */
    @TableField("film_publish_country")
    private String filmPublishCountry;

    /**
     * 发行时间
     */
    @TableField("film_publish_time")
    private Date filmPublishTime;

    /**
     * 发行地区简称
     * @mock cn
     * @since v1.0
     */
    @TableField("film_lang")
    private String filmLang;

    /**
     * url
     * @mock
     * @since v1.0
     */
    @TableField("film_url")
    private String filmUrl;

    /**
     * 电影封面
     * @mock https://wmdb.querydata.org/movie/poster/1603701754760-c50d8a.jpg
     * @since v1.0
     */
    @TableField("film_poster")
    private String filmPoster;

    /**
     * 卡片
     * @mock https://wmdb.querydata.org/movie/poster/1605355459683-5f968bfaee3680299115bb97.png
     * @since v1.0
     */
    @TableField("film_shareImage")
    private String filmShareimage;

    /**
     * 简介
     */
    @TableField("film_description")
    private String filmDescription;

    /**
     * 创建时间
     */
    @TableField("creact_time")
    private Date creactTime;

    /**
     * 创建人
     */
    @TableField("creator")
    private String creator;

    /**
     * 导演
     * @mock 1
     * @since v1.0
     */
    @TableField("film_director")
    private String filmDirector;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
