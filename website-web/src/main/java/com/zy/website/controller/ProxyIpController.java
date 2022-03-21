package com.zy.website.controller;


import com.zy.website.service.ProxyIpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 *  IP代理池
 * @author zhangyu
 * @since 2022-03-19
 */
@RestController
@RequestMapping("/proxyIp")
public class ProxyIpController extends BaseController {
    private static Logger logger = LogManager.getLogger(ProxyIpController.class);
    @Resource
    private ProxyIpService proxyIpService;


    /**
     *  批量爬取IP
     * @author zhangyu
     * @date 2022/3/21 13:48
     * @return void
     */
    @RequestMapping(value = "getIp", method = RequestMethod.GET)
    public void getPlayRecord() {
        proxyIpService.getProxyJob();
    }

}

