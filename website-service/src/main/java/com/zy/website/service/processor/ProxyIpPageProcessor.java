package com.zy.website.service.processor;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 *  用于解析爬取到的XXX html页面
 **/
public class ProxyIpPageProcessor implements PageProcessor {

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    // webmagic官方还有很多案例，更多内容自行参考学习了，例如配置代理，自带url去重、网页去重等功能
    // 官方文档地址：http://webmagic.io/docs/zh/
    private String url = "https://proxy.mimvp.com/freeopen";

    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        //用于获取所有满足这个正则表达式的链接
        List<String> links = page.getHtml().links().regex("https://proxy.mimvp\\.com/freeopen?proxy=in_hp&sort=&page=52").all();

    }

    @Override
    public Site getSite() {
        return MySite.getSit(site);
    }
}
