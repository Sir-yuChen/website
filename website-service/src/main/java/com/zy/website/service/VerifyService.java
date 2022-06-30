package com.zy.website.service;

import com.zy.website.facade.ApiReturn;

import javax.servlet.http.HttpServletRequest;

/**
 * @param $
 * @author zhangyu
 * @description $
 * @date $ $
 * @return $
 * @since
 **/
public interface VerifyService {
    ApiReturn getVerifyCode(HttpServletRequest request);

}
