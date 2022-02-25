package com.zy.website.aop;

import cn.hutool.core.util.ReflectUtil;
import com.alibaba.fastjson.JSON;
import com.zy.website.utils.DateUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Configuration
@Aspect
@Order(2)//多个aop执行顺序 也可以实现Ordered接口 返回顺序值
public class LogServiceAspect {

    private static Logger logger = LogManager.getLogger(LogServiceAspect.class);

    //定义切入点,拦截controller包其子包下的所有类的所有方法
    @Pointcut("execution(* com.zy.website.controller..*.*(..))")
    public void pointCut() {
    }

    //执行方法前的拦截方法
    @Before("pointCut()")
    public void doBeforeMethod(JoinPoint point) {
        //RequestContextHolder：持有上下文的Request容器,获取到当前请求的request
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest httpServletRequest = sra.getRequest();
        String requestURI = httpServletRequest.getRequestURI();

        //这一步获取到的方法有可能是代理方法也有可能是真实方法
        Method method = ((MethodSignature) point.getSignature()).getMethod();
        //判断代理对象本身是否是连接点所在的目标对象，不是的话就要通过反射重新获取真实方法
        if (point.getThis().getClass() != point.getTarget().getClass()) {
            method = ReflectUtil.getMethod(point.getTarget().getClass(), method.getName(), method.getParameterTypes());
        }
        //通过真实方法获取该方法的参数名称
        LocalVariableTableParameterNameDiscoverer paramNames = new LocalVariableTableParameterNameDiscoverer();
        String[] parameterNames = paramNames.getParameterNames(method);

        //获取连接点方法运行时的入参列表
        Object[] args = point.getArgs();
        //将参数名称与入参值一一对应起来
        Map<String, Object> params = new HashMap<>();
        for (int i = 0; i < parameterNames.length; i++) {
            //请求参数为dto 这map key值是request
            params.put(parameterNames[i], args[i]);
        }
        String requestParams;
        if (Arrays.asList(parameterNames).contains("response")) {
            params.remove("response");
        }
        try {
            if (Arrays.asList(parameterNames).contains("request")) {
                requestParams = JSON.toJSONString(params.get("request"));
            }else {
                requestParams = JSON.toJSONString(params);
            }
        } catch (Exception e) {
            requestParams = params.toString();
        }
        logger.info("请求地址 requestURL=【{}】", requestURI);
        logger.info("请求方式 requestMODE=【{}】", httpServletRequest.getMethod());
        logger.info("请求 IP  requestIP=【{}】", httpServletRequest.getRemoteAddr());
        logger.info("请求时间 requestTime=【{}】", DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HHMMSS));
        logger.info("请求方法 requestTime=【{}】", method.getName());
        logger.info("请求参数 requestParams=【{}】", requestParams);
    }

    /**
     * 后置返回通知
     * 这里需要注意的是:
     * 如果参数中的第一个参数为point，则第二个参数为返回值的信息
     * 如果参数中的第一个参数不为point，则第一个参数为returning中对应的参数
     * returning 限定了只有目标方法返回值与通知方法相应参数类型时才能执行后置返回通知，否则不执行，对于returning对应的通知方法参数为Object类型将匹配任何目标返回值
     */
    @AfterReturning(pointcut = "pointCut()", returning = "result")
    public void doAfterReturningAdvice1(JoinPoint point, Object result) {
    }

    /**
     * 异常返回通知，用于拦截异常日志信息 连接点抛出异常后执行
     *
     * @param joinPoint 切入点
     * @param e         异常信息
     */
    @AfterThrowing(pointcut = "pointCut()", throwing = "e")
    public void saveExceptionLog(JoinPoint joinPoint, Throwable e) {
        // 获取RequestAttributes
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        // 从获取RequestAttributes中获取HttpServletRequest的信息
        HttpServletRequest request = (HttpServletRequest) requestAttributes
                .resolveReference(RequestAttributes.REFERENCE_REQUEST);
        try {
            // 从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            // 获取切入点所在的方法
            Method method = signature.getMethod();
            // 获取请求的类名
            String className = joinPoint.getTarget().getClass().getName();
            // 获取请求的方法名
            String methodName = method.getName();
            methodName = className + "." + methodName;
            // 请求的参数
            Map<String, String> rtnMap = converMap(request.getParameterMap());
            // 将参数所在的数组转换成json
            String params = JSON.toJSONString(rtnMap);

            logger.error("==============================AOP异常通知========================");
            logger.error("异常时间  Error Time【{}】", DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HHMMSS));
            logger.error("异常 IP  Error IP【{}】", request.getRemoteAddr());
            logger.error("异常方法 Error MethodName【{}】", methodName);
            logger.error("异常名称 Error Name【{}】",e.getClass().getName());
            logger.error("接口参数 Error Params【{}】",params);
            logger.error("异常信息 Error Msg=【{}】", e.getMessage());

        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    //    后置最终通知（目标方法只要执行完了就会执行后置通知方法）
    @After("pointCut()")
    public void doAfterAdvice(JoinPoint point) {
        logger.info("AOP 后置通知");
    }


    /*环绕通知：
     * 环绕通知非常强大，可以决定目标方法是否执行，什么时候执行，执行时是否需要替换方法参数，执行完毕是否需要替换返回值。
     * 环绕通知第一个参数必须是org.aspectj.lang.Proceedingpoint类型
     */
/*    @Around(ExpGetResultDataPonit)
    public Object doAroundAdvice(Proceedingpoint proceedingpoint) {
        System.out.println("环绕通知的目标方法名：" + proceedingpoint.getSignature().getName());
        processInputArg(proceedingpoint.getArgs());
        try {//obj之前可以写目标方法执行前的逻辑
            Object obj = proceedingpoint.proceed();//调用执行目标方法
            processOutPutObj(obj);
            return obj;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }*/


    /**
     * 转换request 请求参数
     *
     * @param paramMap request获取的参数数组
     */
    public Map<String, String> converMap(Map<String, String[]> paramMap) {
        Map<String, String> rtnMap = new HashMap<String, String>();
        for (String key : paramMap.keySet()) {
            rtnMap.put(key, paramMap.get(key)[0]);
        }
        return rtnMap;
    }

}
