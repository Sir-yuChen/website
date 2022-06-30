package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.model.PlayRecordModel;
import com.zy.website.facade.model.dto.PlayRecordDTO;
import com.zy.website.facade.request.PlayClearRecordRequest;
import com.zy.website.facade.response.PlayRecordResponse;

/**
 * @author zhangyu
 * @since 2022-02-26
 */
public interface PlayRecordService extends IService<PlayRecordModel> {

    PlayRecordResponse getPlayRecord(String key);

    ApiReturn setPlayRecord(PlayRecordDTO dto);

    ApiReturn clearPlayRecord(PlayClearRecordRequest playClearRecordRequest, String ipAddr);

}
