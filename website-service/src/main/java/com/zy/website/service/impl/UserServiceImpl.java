package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.facade.request.UserLoginRequest;
import com.zy.website.facade.variable.UrlConstant;
import com.zy.website.service.UserService;
import com.zy.website.utils.RestTemplateUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Administrator
 * @date 2022/7/2 22:59
 **/
@Service("userService")
public class UserServiceImpl implements UserService {

    private static Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Resource
    RestTemplateUtils restTemplateUtils;

    @Override
    public ApiReturn userLogin(UserLoginRequest request) {
        ApiReturn apiReturn = null;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("uname", request.getUserName());
            params.put("pwd", request.getPwd());
            logger.info("[远程请求 登录接口] 入参:{}", JSONObject.toJSONString(params));
            apiReturn = restTemplateUtils.httpPostJson(UrlConstant.LONG_URL, params, null, ApiReturn.class);
            logger.info("[远程请求 登录接口] 出参:{}", JSONObject.toJSONString(apiReturn));
        } catch (Exception e) {
            logger.error("远程请求 登录接口 异常", JSONObject.toJSONString(request));
            apiReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
        }
        return apiReturn;
    }

    @Override
    public ApiReturn userLogout() {
        ApiReturn apiReturn = null;
        try {
            /*Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", token);*/
            logger.info("[远程请求 退登接口] 入参:{}");
            apiReturn = restTemplateUtils.httpGetPlaceholder(UrlConstant.LONG_OUT_URL, null, null, ApiReturn.class);
            logger.info("[远程请求 退登接口] 出参:{}", JSONObject.toJSONString(apiReturn));
        } catch (Exception e) {
            logger.error("远程请求 退登接口 异常");
            apiReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
        }
        return null;
    }
}
