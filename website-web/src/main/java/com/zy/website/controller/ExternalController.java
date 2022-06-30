package com.zy.website.controller;


import com.alibaba.fastjson.JSONObject;
import com.zy.website.facade.ApiReturn;
import com.zy.website.service.ExternalService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 第三方接口信息
 *
 * @author zhangyu
 * @since 2022-03-02
 */
@RestController
@RequestMapping("/external")
public class ExternalController extends BaseController {

    private static Logger logger = LogManager.getLogger(PlayRecordController.class);

    @Resource
    ExternalService externalService;

    /**
     * 友链
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     * @date 2022/4/9 13:48
     */
    @RequestMapping(value = "friendLinks", method = RequestMethod.GET)
    public ApiReturn getFriendLinks() {
        ApiReturn apiReturn = externalService.getFriendLinks();
        logger.info("友链 接口 出参：{}", JSONObject.toJSONString(apiReturn));
        return apiReturn;
    }


}

