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
 * @since 2022-03-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_external")
public class ExternalModel extends Model<ExternalModel> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 接口平台
     */
    @TableField("platform_name")
    private String platformName;

    /**
     * 接口名称
     */
    @TableField("api_name")
    private String apiName;

    /**
     * 平台标识，自定义
     */
    @TableField("platform_mark")
    private String platformMark;

    /**
     * 域名/IP
     */
    @TableField("domain")
    private String domain;

    /**
     * 具体路径
     */
    @TableField("specific_url")
    private String specificUrl;

    /**
     * 请求方式post/get
     */
    @TableField("request_type")
    private String requestType;

    /**
     * 入参名 逗号分隔
     */
    @TableField("specific_params")
    private String specificParams;

    /**
     * Y/N
     */
    @TableField("status")
    private String status;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    @TableField("create_time")
    private Date createTime;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
