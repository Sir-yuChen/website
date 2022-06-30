package com.zy.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.facade.enums.ExternalEnum;
import com.zy.website.facade.enums.WebsiteStatusEnum;
import com.zy.website.facade.model.ExternalModel;
import com.zy.website.mapper.ExternalMapper;
import com.zy.website.service.ExternalService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class ExternalServiceImpl extends ServiceImpl<ExternalMapper, ExternalModel> implements ExternalService {

    @Resource
    ExternalMapper externalMapper;

    @Override
    public ApiReturn getFriendLinks() {
        ApiReturn apiReturn = new ApiReturn();
        apiReturn.setMsg(ApiReturnCode.SUCCESSFUL.getMessage());
        apiReturn.setCode(ApiReturnCode.SUCCESSFUL.getCode());
        QueryWrapper<ExternalModel> query = new QueryWrapper<>();
        query.lambda().eq(ExternalModel::getPlatformMark, ExternalEnum.FRIEND_LINKS.getCode())
                .eq(ExternalModel::getStatus, WebsiteStatusEnum.STATUS_MENU_Y.getCode())
                .orderByDesc(ExternalModel::getCreateTime)
                .last("limit 10");
        List<ExternalModel> externalModels = externalMapper.selectList(query);
        apiReturn.setData(externalModels);
        return apiReturn;
    }
}
