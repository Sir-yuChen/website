package com.zy.website.test.dubbo;

import com.ant.backstage.facade.serviceInterface.VerficationFacadeService;
import com.zy.website.test.BaseTest;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @param $
 * @author zhangyu
 * @description $
 * @date $ $
 * @return $
 * @since
 **/
public class verficationFacadeServiceTest extends BaseTest {

    @Resource
    VerficationFacadeService verficationFacadeService;

    @Test
    public void getCodeTest() {
      /*  HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        System.out.println("RPC dubbo 获取验证码");
        GetVerificationCodeRequest getVerificationCodeRequest = new GetVerificationCodeRequest();
        getVerificationCodeRequest.setContactType("verifcation");
        getVerificationCodeRequest.setUname("zhangyu");
        ApiReturn apiReturn = verficationFacadeService.getVerificationCode(request, getVerificationCodeRequest);
        System.out.println("RPC dubbo 获取验证码 出参:" + apiReturn.toString());*/
    }

}
