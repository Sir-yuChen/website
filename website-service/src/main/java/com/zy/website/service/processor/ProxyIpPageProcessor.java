package com.zy.website.service.processor;

import com.alibaba.fastjson.JSONObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *  用于解析爬取到的XXX html页面
 **/
public class ProxyIpPageProcessor implements PageProcessor {

    private static Logger logger = LogManager.getLogger(ProxyIpPageProcessor.class);

    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    // webmagic官方还有很多案例，更多内容自行参考学习了，例如配置代理，自带url去重、网页去重等功能
    // 官方文档地址：http://webmagic.io/docs/zh/
    public static void main(String[] args) {
        Spider.create(new ProxyIpPageProcessor())
                //在这里写上自己博客地址，从这个地址开始抓
                .addUrl("http://www.66ip.cn/")
                .addPipeline(new ProxyIpPipeline())
                .run();
    }
    private Integer total = 0;
    private Integer requestCount = 0;
    private List rultes;
    @Override
    // process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
    public void process(Page page) {
        logger.info("获取代理IP池开始 爬虫{}",requestCount);
        List<String> ruls = new ArrayList<>();
        List<String> links =new ArrayList<>();
        if (total == 0) {
            String pagesPath_t="http://www\\.66ip\\.cn/[0-9]+\\.html";
            List<Integer> integers = new ArrayList<>();
            ruls.add(pagesPath_t);
            for (int i = 0; i < ruls.size(); i++) {
                //用于获取所有满足这个正则表达式的链接
                List<String> all = page.getHtml().links().regex(ruls.get(i)).all().stream().distinct().collect(Collectors.toList());
                all.forEach(path->{
                    int index = path.lastIndexOf("/");
                    String substring = path.substring(index + 1);
                    int index1 = substring.lastIndexOf(".");
                    String number = substring.substring(0, index1);
                    integers.add(Integer.valueOf(number));
                });
            }
            total = integers.stream().distinct().max(Integer::compare).get();
            for (int j = 1; j <= 10; j++) {
                links.add("http://www.66ip.cn/"+j+".html");
            }
            page.addTargetRequests(links);
        }
        //将这些链接加入到待抓取的队列中去
        List<String> list = page.getHtml().xpath("//div[@class='layui-row layui-col-space15']//div[@align='center']//table//tbody//tr").all();
        list.remove(0);
        requestCount++;
        String url1 = page.getRequest().getUrl();
        logger.info("获取代理IP池开始 爬虫  请求URL{} 获取数据{}", url1,JSONObject.toJSONString(list));
        if (rultes == null) {
            rultes = list;
        }else {
            rultes.addAll(list);
        }
        page.setSkip(true);
        if (requestCount > 1) {
            int i = url1.lastIndexOf("/") + 1;
            int i1 = url1.lastIndexOf(".");
            String pageNum = url1.substring(i, i1);
            if (Integer.valueOf(pageNum)==10) {
                page.setSkip(false);
                page.putField("rultes",rultes);
                logger.info("获取代理IP池开始 爬虫 =={}", JSONObject.toJSONString(rultes));
            }
        }
    }

    @Override
    public Site getSite() {
        return site;
    }
}
