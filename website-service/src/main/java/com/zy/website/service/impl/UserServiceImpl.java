package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.facade.request.ChekCaptchaRequest;
import com.zy.website.facade.request.UserLoginRequest;
import com.zy.website.facade.variable.UrlConstant;
import com.zy.website.service.UserService;
import com.zy.website.service.VerifyService;
import com.zy.website.utils.RestTemplateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author Administrator
 * @date 2022/7/2 22:59
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Resource
    RestTemplateUtils restTemplateUtils;

    @Resource
    VerifyService verifyService;

    @Override
    public ApiReturn userLogin(UserLoginRequest request) {
        ApiReturn apiReturn = null;
        try {
            //校验验证码
            if (!Optional.ofNullable(request.getVerifyCode()).isPresent() &&
                    !Optional.ofNullable(request.getType()).isPresent() && !Optional.ofNullable(request.getIp()).isPresent()
            ) {
                logger.error("登录校验验证码参数异常 request:{}", JSONObject.toJSONString(request));
                apiReturn.setApiReturnCode(ApiReturnCode.PARAMS_ERROR);
                return apiReturn;
            }
            ChekCaptchaRequest captchaRequest = new ChekCaptchaRequest();
            captchaRequest.setType(request.getType());
            captchaRequest.setUserName(request.getUserName());
            captchaRequest.setVerifyCode(request.getVerifyCode());
            ApiReturn checkVerifyCode = verifyService.checkVerifyCode(request.getIp(), captchaRequest);
            if (!checkVerifyCode.getCode().equals(ApiReturnCode.SUCCESSFUL.getCode())) {
                return checkVerifyCode;
            }
            Map<String, Object> params = new HashMap<>();
            params.put("uname", request.getUserName());
            params.put("pwd", request.getPwd());
            logger.info("[远程请求 登录接口] 入参:{}", JSONObject.toJSONString(params));
            apiReturn = restTemplateUtils.httpPostJson(UrlConstant.LONG_URL, params, null, ApiReturn.class);
            if (!apiReturn.getCode().equals(ApiReturnCode.SUCCESSFUL.getCode())) {
                HttpServletResponse httpResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
                httpResponse.setHeader("Authorization", "");
            }
            logger.info("[远程请求 登录接口] 出参:{}", JSONObject.toJSONString(apiReturn));
        } catch (Exception e) {
            logger.error("远程请求 登录接口 异常", JSONObject.toJSONString(request));
            HttpServletResponse httpResponse = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
            httpResponse.setHeader("Authorization", "");
            apiReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
        }
        return apiReturn;
    }

    @Override
    public ApiReturn userLogout() {
        ApiReturn apiReturn = null;
        try {
            logger.info("[远程请求 退登接口] 入参:{}");
            apiReturn = restTemplateUtils.httpGetPlaceholder(UrlConstant.LONG_OUT_URL, null, null, ApiReturn.class);
            logger.info("[远程请求 退登接口] 出参:{}", JSONObject.toJSONString(apiReturn));
        } catch (Exception e) {
            logger.error("远程请求 退登接口 异常");
            apiReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
        }
        return apiReturn;
    }

    @Override
    public ApiReturn queryCurrentUser(String token) {
        ApiReturn apiReturn = null;
        try {
            logger.info("[远程请求 获取用户信息接口] 入参:{}");
            apiReturn = restTemplateUtils.httpGetPlaceholder(UrlConstant.QUERY_USER_INFO, null, null, ApiReturn.class);
            logger.info("[远程请求 获取用户信息接口] 出参:{}", JSONObject.toJSONString(apiReturn));
          /*  if (apiReturn.getCode().equals(ApiReturnCode.SUCCESSFUL.getCode())) {
                UserInfoDTO data = (UserInfoDTO) apiReturn.getData();
                apiReturn.setData(data);
            }*/
        } catch (Exception e) {
            logger.error("远程请求 获取用户信息接口 异常");
            apiReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
        }
        return apiReturn;
    }
}
