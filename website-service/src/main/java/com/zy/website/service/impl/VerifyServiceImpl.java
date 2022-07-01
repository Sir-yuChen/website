package com.zy.website.service.impl;

import com.ant.backstage.facade.code.ApiReturnCode;
import com.ant.backstage.facade.request.CheckVerifyCodeRequest;
import com.ant.backstage.facade.request.GetVerificationCodeRequest;
import com.ant.backstage.facade.serviceInterface.VerficationFacadeService;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.exception.WebsiteBusinessException;
import com.zy.website.facade.request.CaptchaRequest;
import com.zy.website.facade.request.ChekCaptchaRequest;
import com.zy.website.service.VerifyService;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service("verifyService")
public class VerifyServiceImpl implements VerifyService {
    private static Logger logger = LogManager.getLogger(VerifyServiceImpl.class);

    @Resource
    VerficationFacadeService verficationFacadeService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public ApiReturn getVerifyCode(String ipAddress, CaptchaRequest captchaRequest) {
        ApiReturn aReturn = null;
        try {
            GetVerificationCodeRequest verificationCodeRequest = new GetVerificationCodeRequest();
            verificationCodeRequest.setUname(captchaRequest.getUserUid());
            verificationCodeRequest.setContactType(captchaRequest.getType());
            verificationCodeRequest.setCurrentIp(ipAddress);
            com.ant.backstage.facade.ApiReturn apiReturn = verficationFacadeService.getVerificationCode(verificationCodeRequest);
            aReturn = mapperFacade.map(apiReturn, com.zy.website.facade.ApiReturn.class);
        } catch (Exception e) {
            logger.error("验证码获取异常");
            throw new WebsiteBusinessException(ApiReturnCode.HTTP_ERROR.getMessage(), ApiReturnCode.HTTP_ERROR.getCode());
        }
        return aReturn;
    }

    @Override
    public ApiReturn checkVerifyCode(String ipAddress, ChekCaptchaRequest chekCaptchaRequest) {
        ApiReturn aReturn = null;
        try {
            CheckVerifyCodeRequest request = new CheckVerifyCodeRequest();
            request.setCurrentIp(ipAddress);
            request.setContactType(chekCaptchaRequest.getType());
            request.setUname(chekCaptchaRequest.getUserUid());
            request.setVerifyCode(chekCaptchaRequest.getVerifyCode());
            com.ant.backstage.facade.ApiReturn apiReturn = verficationFacadeService.checkVerificationCode(request);
            aReturn = mapperFacade.map(apiReturn, ApiReturn.class);
        } catch (Exception e) {
            logger.error("验证码校验失败");
            aReturn.setApiReturnCode(com.zy.website.facade.code.ApiReturnCode.HTTP_ERROR);
        }
        return aReturn;
    }

}
