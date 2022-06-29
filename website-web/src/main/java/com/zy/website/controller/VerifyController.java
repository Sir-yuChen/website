package com.zy.website.controller;

import com.zy.website.ApiReturn;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.model.vo.CaptchaVO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 随机验证码
     *
     * @return com.zy.website.ApiReturn
     * @author zhangyu
     */
    @RequestMapping(value = "captcha", method = RequestMethod.GET)
    public ApiReturn getCaptcha() {
        ApiReturn apiReturn = new ApiReturn();
        logger.info("获取验证码===>");
        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        apiReturn.setData(new CaptchaVO().setCaptchaCode("123456"));
        return apiReturn;
    }


}
