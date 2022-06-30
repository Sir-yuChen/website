package com.zy.website.facade.request;

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

    /***
      * 电影名称
      * @mock 肖申克救赎
      * @since
      */
    private String filmName;

    /***
     * 电影唯一uid
     * @mock 5f968bfaee3680299115bb97
     * @since
     */
    private String playFilmUid;

    /***
     * 播放地址
     * @mock https://cn.bing.com/
     * @since
     */
    private String filmUrl;

    /***
     * 播放时长
     */
    private Integer playDuration;


}
