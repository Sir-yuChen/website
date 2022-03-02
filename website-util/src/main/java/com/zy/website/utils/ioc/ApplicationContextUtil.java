package com.zy.website.utils.ioc;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @date 2021/12/23 12:53
 **/
@Component
public class ApplicationContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;

    //将IOC容器回调给我们，我们将它缓存起来
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        if(ApplicationContextUtil.context == null){
            ApplicationContextUtil.context  = applicationContext;
        }
    }
    //根据bean名字获取工厂中指定bean 对象        从IOC容器中获取组件（bean）
    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name){
        return getApplicationContext().getBean(name);
    }

    public static <T> T getBean(Class<T> clazz){
        return getApplicationContext().getBean(clazz);
    }

    public static <T> T getBean(String name,Class<T> clazz){
        return getApplicationContext().getBean(name, clazz);
    }


}

