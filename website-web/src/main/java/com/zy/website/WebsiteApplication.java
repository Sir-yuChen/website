package com.zy.website;

import com.github.ltsopensource.spring.boot.annotation.EnableJobClient;
import com.github.ltsopensource.spring.boot.annotation.EnableJobTracker;
import com.github.ltsopensource.spring.boot.annotation.EnableMonitor;
import com.github.ltsopensource.spring.boot.annotation.EnableTaskTracker;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@MapperScan("com.zy.website.mapper")
@ServletComponentScan //war
@SpringBootApplication
@EnableJobClient        //JobClient      lts
@EnableTaskTracker      //TaskTracker    lts
@EnableJobTracker       //JobTracker     lts
@EnableMonitor          //Monitor        lts
public class WebsiteApplication extends SpringBootServletInitializer {

    //因为是父子项目 需要制定一个主启动类打war  SpringBootServletInitializer
    @Override//重写configure方法
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebsiteApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(WebsiteApplication.class, args);
        System.out.println("Application is Started !!! ");
    }

}
