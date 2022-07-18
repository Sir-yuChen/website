package com.zy.website.utils.annotation;

import java.lang.annotation.*;

/**
 * 限流注解
 * @author Administrator
 */
@Inherited
@Documented
@Target(ElementType.METHOD)//作用方法上
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimitNote {

    // 默认每秒放入桶中的token
    double limitNum() default 20;

    String name() default "";

}
