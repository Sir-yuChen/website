package com.zy.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.mapper.ProxyIpMapper;
import com.zy.website.model.ProxyIpModel;
import com.zy.website.service.ProxyIpService;
import com.zy.website.service.processor.ProxyIpPageProcessor;
import com.zy.website.service.processor.ProxyIpPipeline;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;

/**
 * @author zhangyu
 * @since 2022-03-19
 */
@Service("proxyIpService")
public class ProxyIpServiceImpl extends ServiceImpl<ProxyIpMapper, ProxyIpModel> implements ProxyIpService {

    private static Logger logger = LogManager.getLogger(ProxyIpServiceImpl.class);

    @Override
    public void getProxyJob() {
        Spider.create(new ProxyIpPageProcessor())
                //添加初始的URL
                .addUrl("http://www.66ip.cn/")
                //添加一个Pipeline，一个Spider可以有多个Pipeline
                .addPipeline(new ProxyIpPipeline())
                // 设置布隆过滤器去重操作（默认使用HashSet来进行去重，占用内存较大；使用BloomFilter来进行去重，占用内存较小，但是可能漏抓页面）
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                //开启5个线程抓取
                .thread(10)
                .run();
    }

}
