package com.zy.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.request.CaptchaRequest;
import com.zy.website.facade.request.ChekCaptchaRequest;
import com.zy.website.service.VerifyService;
import com.zy.website.utils.IpAddrUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 校验 VerifyController
 *
 * @author zhangyu
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/api/verify")
public class VerifyController extends BaseController {

    private static Logger logger = LogManager.getLogger(VerifyController.class);

    @Resource
    VerifyService verifyService;

    /**
     * 随机验证码
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @RequestMapping(value = "captcha", method = RequestMethod.POST)
    public ApiReturn getCaptcha(HttpServletRequest request, @RequestBody CaptchaRequest captchaRequest) {
        logger.info("获取验证码 入参:{}", JSONObject.toJSONString(captchaRequest));
        String ipAddress = IpAddrUtil.getIpAddress(request);
        ApiReturn apiReturn = verifyService.getVerifyCode(ipAddress, captchaRequest);
        return apiReturn;
    }

    /**
     * 验证码校验
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @RequestMapping(value = "checkCaptcha", method = RequestMethod.POST)
    public ApiReturn checkCaptcha(HttpServletRequest request, @RequestBody ChekCaptchaRequest chekCaptchaRequest) {
        logger.info("校验验证码 入参:{}", JSONObject.toJSONString(chekCaptchaRequest));
        String ipAddress = IpAddrUtil.getIpAddress(request);
        ApiReturn apiReturn = verifyService.checkVerifyCode(ipAddress, chekCaptchaRequest);
        return apiReturn;
    }


}
