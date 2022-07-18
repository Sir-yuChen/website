package com.zy.website.facade.model;

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
import java.util.Date;

/**
 * @author zhangyu
 * @since 2022-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("t_film_image")
public class FilmImageModel extends Model<FilmImageModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 唯一标识
     */
    @TableField("img_uid")
    private String imgUid;

    /**
     * 展示名称[系统生成]
     */
    @TableField("img_name")
    private String imgName;

    /**
     * 原名
     */
    @TableField("img_original")
    private String imgOriginal;

    /**
     * 状态 Y正常 N暂停 D删除
     */
    @TableField("img_status")
    private String imgStatus;

    /**
     * 归类
     */
    @TableField("img_type")
    private String imgType;

    /**
     * 后缀
     */
    @TableField("img_suffix")
    private String imgSuffix;

    /**
     * 图片像素 水平
     */
    @TableField("img_size_level")
    private Integer imgSizeLevel;

    /**
     * 图片像素 垂直
     */
    @TableField("img_size_plumb")
    private Integer imgSizePlumb;

    /**
     * 备注
     */
    @TableField("img_remark")
    private String imgRemark;
    /**
     * 备注
     */
    @TableField("img_url")
    private String imgUrl;

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
