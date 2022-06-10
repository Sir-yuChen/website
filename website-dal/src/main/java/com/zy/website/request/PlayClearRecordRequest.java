package com.zy.website.request;


import lombok.Data;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
public class PlayClearRecordRequest  implements Serializable {

    /***
      *  播放记录 ID 登录状态下
      * @mock 1,2,3
      * @since
      */
    private String playRecordIds;

    /***
      *  用户ID
      * @mock zhangyu123
      * @since
      */
    private String userUid;

}
