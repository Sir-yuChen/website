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

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author zhangyu
 * @since 2022-03-19
 */
@Service("proxyIpService")
public class ProxyIpServiceImpl extends ServiceImpl<ProxyIpMapper, ProxyIpModel> implements ProxyIpService {

    private static Logger logger = LogManager.getLogger(ProxyIpServiceImpl.class);

    //ConcurrentLinkedQueue来保存可以使用的ip,使用完了再重新添加进队列,因为ConcurrentLinkedQueue是线程安全的,poll操作的时候不会出现两个任务同时使用一个ip
    public static ConcurrentLinkedQueue<ProxyIpModel> proxyIps = new ConcurrentLinkedQueue<>();

    @Override
    public void getProxyJob() {
        Spider.create(new ProxyIpPageProcessor())
                //在这里写上自己博客地址，从这个地址开始抓
                .addUrl("http://www.66ip.cn/")
                .addPipeline(new ProxyIpPipeline())
                // 设置布隆过滤器去重操作（默认使用HashSet来进行去重，占用内存较大；使用BloomFilter来进行去重，占用内存较小，但是可能漏抓页面）
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)))
                //开启5个线程抓取
                .thread(10)
                .run();
    }

}
