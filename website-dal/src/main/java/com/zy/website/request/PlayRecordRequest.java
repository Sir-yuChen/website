package com.zy.website.request;

import lombok.Data;

import java.io.Serializable;

/**
 *  播放记录 request
 * @author zhangyu
 * @description
 * @date 2022/3/1 17:18
 * @return
 */
@Data
public class PlayRecordRequest implements Serializable {

    /**
     * 电影名称
     */
    private String filmName;

    /**
     * 电影唯一uid
     */
    private String playFilmUid;

    /**
     * 视频地址
     */
    private String filmUrl;

    /**
     * 播放时长
     */
    private Integer playDuration;


}
