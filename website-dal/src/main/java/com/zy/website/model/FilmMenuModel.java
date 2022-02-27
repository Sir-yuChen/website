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
import java.util.Date;

/**
 *
 * @author zhangyu
 * @since 2022-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL) //为null的字段不返回
@TableName("t_film_menu")
public class FilmMenuModel extends Model<FilmMenuModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单父ID 顶级0
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 菜单名称
     */
    @TableField("menu_name")
    private String menuName;

    /**
     * 唯一标识
     */
    @TableField("menu_mark")
    private String menuMark;

    /**
     * 状态 Y正常 N暂停 D删除
     */
    @TableField("menu_status")
    private String menuStatus;

    /**
     * 排序
     */
    @TableField("menu_sequence")
    private Integer menuSequence;

    /**
     * 图标地址
     */
    @TableField("menu_icon_url")
    private String menuIconUrl;

    /**
     * 跳转地址
     */
    @TableField("menu_url")
    private String menuUrl;

    /**
     * 创建时间
     */
    @TableField("creact_time")
    private Date creactTime;

    /**
     * 菜单类型[菜单用户]
     */
    @TableField("menu_type")
    private String menuType;
    /**
     * 是否有子类 true/false
     */
    @TableField("menu_is_child")
    private Boolean menuIsChild;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
