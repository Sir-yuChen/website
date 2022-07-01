package com.zy.website.service;

import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.request.CaptchaRequest;
import com.zy.website.facade.request.ChekCaptchaRequest;

/**
 * @param $
 * @author zhangyu
 * @description $
 * @date $ $
 * @return $
 * @since
 **/
public interface VerifyService {
    ApiReturn getVerifyCode(String ipAddress, CaptchaRequest captchaRequest);

    ApiReturn checkVerifyCode(String ipAddress, ChekCaptchaRequest chekCaptchaRequest);

}
