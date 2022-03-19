package com.zy.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.mapper.ProxyIpMapper;
import com.zy.website.model.ProxyIpModel;
import com.zy.website.service.ProxyIpService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author zhangyu
 * @since 2022-03-19
 */
@Service("proxyIpService")
public class ProxyIpServiceImpl extends ServiceImpl<ProxyIpMapper, ProxyIpModel> implements ProxyIpService {

    private static Logger logger = LogManager.getLogger(ProxyIpServiceImpl.class);

    @Resource
    ProxyIpMapper proxyIpMapper;

    //ConcurrentLinkedQueue来保存可以使用的ip,使用完了再重新添加进队列,因为ConcurrentLinkedQueue是线程安全的,poll操作的时候不会出现两个任务同时使用一个ip
    public static ConcurrentLinkedQueue<ProxyIpModel> proxyIps = new ConcurrentLinkedQueue<>();

    @Override
    public void getProxyJob() {

    }

}
