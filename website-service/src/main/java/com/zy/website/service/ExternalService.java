package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.model.ExternalModel;

/**
 * @author Administrator
 */
public interface ExternalService extends IService<ExternalModel> {

    ApiReturn getFriendLinks();

}
