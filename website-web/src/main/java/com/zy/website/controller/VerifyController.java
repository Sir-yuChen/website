package com.zy.website.controller;

import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.facade.model.vo.CaptchaVO;
import com.zy.website.service.VerifyService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public ApiReturn getCaptcha(HttpServletRequest request) {
        logger.info("获取验证码===>");
        ApiReturn apiReturn = verifyService.getVerifyCode(request);
        return apiReturn;
    }


}
