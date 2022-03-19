package com.zy.website.service.processor;

import com.zy.website.mapper.ProxyIpMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;

/**
 * 用于将XXX html页面解析出的数据存储到mysql数据库
 **/
@Component("proxyIpPipeline")
public class ProxyIpPipeline implements Pipeline {

    private static Logger logger = LogManager.getLogger(ProxyIpPipeline.class);

    @Resource
    ProxyIpMapper proxyIpMapper;

    @Override
    public void process(ResultItems resultItems, Task task) {




    }
}
