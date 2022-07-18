package com.zy.website.aop;

import com.google.common.util.concurrent.RateLimiter;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.utils.annotation.RateLimitNote;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
@Order(3)//多个aop执行顺序 也可以实现Ordered接口 返回顺序值
public class RateLimitAspect {

    private static Logger logger = LogManager.getLogger(RateLimitAspect.class);

    private ConcurrentHashMap<String, RateLimiter> RATE_LIMITER = new ConcurrentHashMap<>();
    private RateLimiter rateLimiter;

    @Pointcut("@annotation(com.zy.website.utils.annotation.RateLimitNote)")
    public void serviceLimit() {
    }

    @Around("serviceLimit()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        Object obj = null;
        //获取拦截的方法名
        Signature sig = point.getSignature();
        //获取拦截的方法名
        MethodSignature msig = (MethodSignature) sig;
        //返回被织入增加处理目标对象
        Object target = point.getTarget();
        //为了获取注解信息
        Method currentMethod = target.getClass().getMethod(msig.getName(), msig.getParameterTypes());
        //获取注解信息
        RateLimitNote annotation = currentMethod.getAnnotation(RateLimitNote.class);
        double limitNum = annotation.limitNum(); //获取注解每秒加入桶中的token
        String functionName = msig.getName(); // 注解所在方法名区分不同的限流策略

        if (RATE_LIMITER.containsKey(functionName)) {
            rateLimiter = RATE_LIMITER.get(functionName);
        } else {
            RATE_LIMITER.put(functionName, RateLimiter.create(limitNum));
            rateLimiter = RATE_LIMITER.get(functionName);
        }
        if (rateLimiter.tryAcquire()) {
            return point.proceed();
        } else {
            logger.warn("请求繁忙 【限流接口:{}】", functionName);
            ApiReturn apiReturn = new ApiReturn();
            apiReturn.setApiReturnCode(ApiReturnCode.PLEASE_AGAIN_LATER);
            return apiReturn;
        }
    }
}