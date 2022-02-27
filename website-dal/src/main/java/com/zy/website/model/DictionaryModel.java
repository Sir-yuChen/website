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
@JsonInclude(JsonInclude.Include.NON_NULL)
@TableName("t_dictionary")
public class DictionaryModel extends Model<DictionaryModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典类型唯一uid
     */
    @TableField("dic_type_uid")
    private String dicTypeUid;

    /**
     * 字典类型KEY
     */
    @TableField("dic_type_key")
    private String dicTypeKey;

    /**
     * 字典类型value
     */
    @TableField("dic_type_value")
    private String dicTypeValue;

    /**
     * 状态 Y正常 N暂停 D删除
     */
    @TableField("dic_type_status")
    private String dicTypeStatus;

    /**
     * 字典类型描述
     */
    @TableField("dic_type_desc")
    private String dicTypeDesc;

    /**
     * 备注
     */
    @TableField("dic_remark")
    private String dicRemark;

    /**
     * 更新时间
     */
    @TableField("update_time")
    private Date updateTime;

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
