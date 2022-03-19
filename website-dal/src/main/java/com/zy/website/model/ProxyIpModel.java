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
 * @since 2022-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_proxy_ip")
public class ProxyIpModel extends Model<ProxyIpModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 获取IP的平台域名
     */
    @TableField("platform_address")
    private String platformAddress;

    /**
     * IP详情
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * IP类型
     */
    @TableField("ip_type")
    private String ipType;

    /**
     * IP状态 1可用 0不可用
     */
    @TableField("ip_status")
    private Integer ipStatus;

    /**
     * 验证地址
     */
    @TableField("validate_url")
    private String validateUrl;

    /**
     * 延迟时效
     */
    @TableField("delat_time")
    private Integer delatTime;

    /**
     * 验证次数
     */
    @TableField("validate_count")
    private Integer validateCount;

    /**
     * 最后验证时间
     */
    @TableField("validate_date")
    private Date validateDate;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
