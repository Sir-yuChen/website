package com.zy.website.service;

import com.zy.website.facade.model.ProxyIpModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @author zhangyu
 * @since 2022-03-19
 */
public interface ProxyIpService extends IService<ProxyIpModel> {

    void getProxyJob();

}
