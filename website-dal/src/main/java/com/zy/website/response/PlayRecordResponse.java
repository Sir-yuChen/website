package com.zy.website.response;

import com.zy.website.model.PlayRecordModel;
import lombok.Data;

import java.util.List;

/**
 *  播放记录 esponse
 * @author zhangyu
 * @description
 * @date 2022/3/1 17:18
 * @return
 */
@Data
public class PlayRecordResponse extends FrontResponse{

    /***
      * 播放记录
      */
    private List<PlayRecordModel> playRecordList;

}
