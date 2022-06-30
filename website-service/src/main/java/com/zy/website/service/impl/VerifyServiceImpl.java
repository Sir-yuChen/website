package com.zy.website.service.impl;

import com.ant.backstage.facade.request.GetVerificationCodeRequest;
import com.ant.backstage.facade.serviceInterface.VerficationFacadeService;
import com.zy.website.facade.ApiReturn;
import com.zy.website.service.VerifyService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Service("verifyService")
public class VerifyServiceImpl implements VerifyService {

    @Resource
    VerficationFacadeService verficationFacadeService;
    @Resource
    MapperFacade mapperFacade;

    @Override
    public ApiReturn getVerifyCode(HttpServletRequest request) {
        GetVerificationCodeRequest verificationCodeRequest = new GetVerificationCodeRequest();
        verificationCodeRequest.setUname("123456");//测试使用
        verificationCodeRequest.setContactType("verifcation");
        com.ant.backstage.facade.ApiReturn apiReturn = verficationFacadeService.getVerificationCode(request, verificationCodeRequest);
        ApiReturn apiReturn1 = mapperFacade.map(apiReturn, ApiReturn.class);
        return apiReturn1;
    }
}
