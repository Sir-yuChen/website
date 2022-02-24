package com.zy.website.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author zhangyu
 * @Description
 * @Date 13:59 2022/2/23
 * @Param  mybatis-plus
 * @return
 **/
@Configuration
@PropertySource("classpath:application.properties")
public class MyBatisPlusConfig {

    /*
     * @ClassName MyBatisPlusConfig
     * @Desc    mybatis-plus 配置拦截
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置方言
        paginationInterceptor.setDialectType("mysql");
        return paginationInterceptor;
    }


}
