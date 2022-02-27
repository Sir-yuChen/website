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
@TableName("t_film_type")
@JsonInclude(JsonInclude.Include.NON_NULL) //为null的字段不返回
public class FilmTypeModel extends Model<FilmTypeModel> implements Serializable {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型名称
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 类型标识唯一
     */
    @TableField("type_mark")
    private String typeMark;

    /**
     * 展示类型名称
     */
    @TableField("type_reveal_name")
    private String typeRevealName;

    /**
     * 类型归类：普通类型TYPE；电影榜类型NOTICE
     */
    @TableField("type_assort")
    private String typeAssort;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
