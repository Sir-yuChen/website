package com.zy.website.aop;

import com.zy.website.request.FrontRequest;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 请求参数验证
 */
@Aspect
@Component
public class WebRequestAspect {

    private static Logger logger = LogManager.getLogger(WebRequestAspect.class);

    /**
     * @within :使用 “@within(注解类型)” 匹配所以持有指定注解类型内的方法;注解类型也必须是全限定类型名;
     * @annotation :使用 “@annotation(注解类型)” 匹配当前执行方法持有指定注解的方法;注解类型也必须是全限定类型名;
     * @args 任何一个只接受一个参数的方法, 且方法运行时传入的参数持有注解动态切入点, 类似于 arg 指示符;
     * @target 任何目标对象持有 Secure 注解的类方法;必须是在目标对象上声明这个注解,在接口上声明的对它不起作用
     * @args :使用 “@args( 注解列表 )” 匹配当前执行的方法传入的参数持有指定注解的执行;注解类型也必须是全限定类型名;
     *
     * expression表达式解析;
     *  定义一个切入点.
     *  解释下：
     *      ~ 第一个 * 代表任意修饰符及任意返回值.
     *      ~ 第二个 * 任意包名
     *      ~ 第三个 * 代表任意方法.
     *      ~ 第四个 * 定义在web包或者子包
     *      ~ 第五个 * 任意方法
     *      ~ .. 匹配任意数量的参数.
     *
     */
    @Pointcut("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public void pointcut() {
    }

    @Before("pointcut()")
    public void doBefore(JoinPoint joinPoint) {
        try {
            //    ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            // 以下是请求内容
            Object[] args = joinPoint.getArgs();
            if (ArrayUtils.isNotEmpty(args)) {
                for(int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (arg != null && arg instanceof com.zy.website.request.FrontRequest) {
                        //把请求headers中的参数设置到请求body的参数里
                        com.zy.website.request.FrontRequest baseInputRequest = (FrontRequest) arg;
//                        ValidationUtils.validate(baseInputRequest);//参数验证不通过会抛出BusinessException异常
                    }
                }
            }
        } catch (Throwable throwable) {
//            if (throwable instanceof ValidatedException){
//                    throw throwable;
//            }
        }
    }

}

