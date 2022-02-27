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
@TableName("t_play_record")
public class PlayRecordModel extends Model<PlayRecordModel> {

    private static final long serialVersionUID=1L;

      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 播放的电影
     */
    @TableField("play_film_uid")
    private String playFilmUid;

    /**
     * 播放时间
     */
    @TableField("play_time")
    private Date playTime;

    /**
     * 播放时间
     */
    @TableField("play_account")
    private Date playAccount;

    /**
     * 播放时长
     */
    @TableField("play_duration")
    private Integer playDuration;

    /**
     * 客户端IP
     */
    @TableField("play_ip")
    private String playIp;

    /**
     * 电影名称
     */
    @TableField("film_name")
    private String filmName;

    /**
     * 跳转地址
     */
    @TableField("film_url")
    private String filmUrl;

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
