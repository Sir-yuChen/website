package com.zy.website.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
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
@TableName("t_film_score")
@JsonInclude(JsonInclude.Include.NON_NULL) //为null的字段不返回
public class FilmScoreModel extends Model<FilmScoreModel>  {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 评分平台名称
     */
    @TableField("score_platform")
    private String scorePlatform;

    /**
     * 平台展示图标
     */
    @TableField("score_platform_icon")
    private String scorePlatformIcon;

    /**
     * 评分总次数
     */
    @TableField("score_total")
    private Integer scoreTotal;

    /**
     * 评分
     */
    @TableField("score_ratio")
    private BigDecimal scoreRatio;

    /**
     * 展示排序
     */
    @TableField("sequence")
    private Integer sequence;

    /**
     * 视频uID
     */
    @TableField("film_uid")
    private String filmUid;

    /**
     * 创建时间
     */
    @TableField("creact_time")
    private Date creactTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
