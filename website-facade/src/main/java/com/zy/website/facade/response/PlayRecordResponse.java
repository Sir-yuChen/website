package com.zy.website.facade.response;

import com.zy.website.facade.model.PlayRecordModel;
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
