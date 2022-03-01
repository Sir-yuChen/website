package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.ApiReturn;
import com.zy.website.dto.PlayRecordDTO;
import com.zy.website.model.PlayRecordModel;
import com.zy.website.response.PlayRecordResponse;

/**
 * @author zhangyu
 * @since 2022-02-26
 */
public interface PlayRecordService extends IService<PlayRecordModel> {

    PlayRecordResponse getPlayRecord(String ipAddr);

    void setPlayRecord(PlayRecordDTO dto);

    ApiReturn clearPlayRecord(String playRecordIds, String ipAddr);

}
