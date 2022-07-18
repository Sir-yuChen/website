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
@TableName("t_dic_data")
public class DicDataModel extends Model<DicDataModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 字典类型唯一uid
     */
    @TableField("dic_type_uid")
    private String dicTypeUid;

    /**
     * 字典排序
     */
    @TableField("dic_sort")
    private Integer dicSort;

    /**
     * 字典标签
     */
    @TableField("dic_label")
    private String dicLabel;

    /**
     * 字典键值
     */
    @TableField("dic_value")
    private String dicValue;

    /**
     * 是否默认Y/N
     */
    @TableField("dic_default")
    private String dicDefault;

    /**
     * 0默认为不固定,1固定
     */
    @TableField("dic_isFixed")
    private Integer dicIsfixed;

    /**
     * 状态 Y正常 N暂停 D删除
     */
    @TableField("dic_status")
    private String dicStatus;

    /**
     * 父ID
     */
    @TableField("parent_id")
    private Integer parentId;

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
