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

/**
 *
 * @author zhangyu
 * @since 2022-02-26
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("t_type_relation_film")
public class TypeRelationFilmModel extends Model<TypeRelationFilmModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型标识唯一
     */
    @TableField("type_id")
    private Integer typeId;

    /**
     * 视频唯一UID
     */
    @TableField("film_uid")
    private String filmUid;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
