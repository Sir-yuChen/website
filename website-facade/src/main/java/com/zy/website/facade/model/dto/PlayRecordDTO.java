package com.zy.website.facade.model.dto;


import lombok.Data;

import java.util.Date;

@Data
public class PlayRecordDTO  extends BaseDTO {

    /**
     * 电影名称
     */
    private String filmName;

    /**
     * 电影唯一uid
     */
    private String playFilmUid;
    /**
     * 播放时间
     */
    private Date playTime;

    /**
     * 视频地址
     */
    private String filmUrl;

    /**
     * 播放账号
     */
    private String  playAccount;

    /**
     * 客户端IP
     */
    private String playIp;

    /**
     * 播放时长
     */
    private Integer playDuration;


}