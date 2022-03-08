package com.zy.website.config.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.List;

public class MyEnvironmentPostProcessor implements EnvironmentPostProcessor {

    //配置文件名称匹配
    private ResourcePatternResolver resourceLoader = new PathMatchingResourcePatternResolver();
    //配置文件加载处理器
    private List<PropertySourceLoader> propertySourceLoaders;

    public MyEnvironmentPostProcessor() {
        super();
        this.propertySourceLoaders = SpringFactoriesLoader.loadFactories(PropertySourceLoader.class, getClass().getClassLoader());
    }

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {

        for (PropertySourceLoader loader : this.propertySourceLoaders) {
            for (String fileExtension : loader.getFileExtensions()) {
                //这里定义了一下配置文件的前缀【my-】，加载所有前缀为my-的配置文件。前缀可以自定义
                //注意要加载的自定义配置文件需要和 CustomEnvironmentPostProcessor 在同一个子项目中
                String location = "classpath*:/properties/my-*." + fileExtension;
                try {
                    Resource[] resources = this.resourceLoader.getResources(location);
                    for (Resource resource : resources) {
                        List<PropertySource<?>> propertySources = loader.load(resource.getFilename(), resource);
                        if (!CollectionUtils.isEmpty(propertySources)){
                            System.out.println("加载配置文件：" + propertySources);
                            propertySources.forEach(environment.getPropertySources()::addLast);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("配置文件加载失败：" + e.getMessage());
                }
            }
        }
    }
}
