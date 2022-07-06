package com.zy.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.facade.request.UserLoginRequest;
import com.zy.website.service.UserService;
import com.zy.website.utils.IpAddrUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * 用户相关 UserController
 *
 * @author zhangyu
 * @since 2022-06-29
 */
@RestController
@RequestMapping("/api/user")
public class UserController extends BaseController {

    private static Logger logger = LogManager.getLogger(VerifyController.class);

    @Resource
    UserService userService;

    /**
     * 用户登录
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ApiReturn userLogin(HttpServletRequest http, @RequestBody UserLoginRequest request) {
        logger.info("用户登录 入参:{}", JSONObject.toJSONString(request));
        String ipAddress = IpAddrUtil.getIpAddress(http);
        request.setIp(ipAddress);
        ApiReturn apiReturn = userService.userLogin(request);
        return apiReturn;
    }

    /**
     * 用户退登
     *
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public ApiReturn userLogout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (!Optional.ofNullable(token).isPresent()) {
            ApiReturn aReturn = new ApiReturn();
            aReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
            logger.error("用户退出 未获取到token 无法退出！！");
            return aReturn;
        }
        ApiReturn apiReturn = userService.userLogout();
        return apiReturn;
    }

    /**
     * 获取用户信息
     * @return com.zy.website.facade.ApiReturn
     * @author zhangyu
     */
    @RequestMapping(value = "queryCurrentUser", method = RequestMethod.GET)
    public ApiReturn queryCurrentUser(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (!Optional.ofNullable(token).isPresent()) {
            ApiReturn aReturn = new ApiReturn();
            aReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
            logger.error("未获取到token 无法查询用户信息！！");
            return aReturn;
        }
        ApiReturn apiReturn = userService.queryCurrentUser(token);
        return apiReturn;
    }


}
