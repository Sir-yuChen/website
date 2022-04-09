package com.zy.website.service;

import com.zy.website.ApiReturn;
import com.zy.website.model.ExternalModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author Administrator
 */
public interface ExternalService extends IService<ExternalModel> {

    ApiReturn getFriendLinks();

}
