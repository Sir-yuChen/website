package com.zy.website.aop;

import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 拦截Controller方法默认返回参数，统一处理返回值/响应体，
 * 1、后端Code码转换为前端code码，
 * 2、可以对响应结果加密等处理
 */
@RestControllerAdvice
public class WebResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    private static Logger logger = LogManager.getLogger(WebResponseBodyAdvice.class);

    /**
     *  判断哪些需要拦截
     */
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        try {
            // 响应报文 处理  可以加密 解密等操作
            if (body instanceof ApiReturn) {
                ApiReturn apiReturn = (ApiReturn) body;
//                if (StringUtils.isBlank(apiReturn.getTraceNo())) {
//                    //业务员日志唯一ID
//                    apiReturn.setTraceNo();
//                }
            }
        }catch (Throwable throwable) {
            logger.error("MVC_RSP_BODY 响应报文 处理失败 body={}", body.toString(), throwable);
            if (body instanceof ApiReturn) {
                ApiReturn apiReturn = (ApiReturn) body;
                apiReturn.setData(null);
                apiReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
            }
        }
        return body;
    }


}
