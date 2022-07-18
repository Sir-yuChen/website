package com.zy.website.controller;


import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.utils.annotation.RateLimitNote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 令牌限流测试 RateLimiterController
 *
 * @author zhangyu
 * @since 2022-07-18
 */
@RequestMapping("/ratelimiter")
@RestController
public class RateLimiterController {

    private static Logger logger = LogManager.getLogger(RateLimiterController.class);


    /**
     * 开启限流【10QPS,策略一】
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @GetMapping("/open")
    @RateLimitNote(limitNum = 10, name = "openRateLimiter1")
    public ApiReturn openRateLimiter1() {
        ApiReturn apiReturn = new ApiReturn();
        logger.info("【openRateLimiter1 限流执行了....编写业务....】");

        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        return apiReturn;
    }

    /**
     * 开启限流【10QPS,策略二】
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @GetMapping("/open2")
    @RateLimitNote(limitNum = 10, name = "openRateLimiter2")
    public ApiReturn openRateLimiter2() {
        ApiReturn apiReturn = new ApiReturn();
        logger.info("【openRateLimiter2 限流执行了....编写业务....】");

        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        return apiReturn;
    }

    /**
     * 未开启限流
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @GetMapping("/close")
    public ApiReturn closeRateLimiter() {
        ApiReturn apiReturn = new ApiReturn();
        logger.info("【closeRateLimiter 接口未开启限流....编写业务....】");

        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        return apiReturn;
    }

}

