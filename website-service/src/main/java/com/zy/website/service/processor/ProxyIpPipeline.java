package com.zy.website.service.processor;

import com.zy.website.mapper.ProxyIpMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.Resource;
import java.util.List;

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
        List<String> rultes = (List<String>) resultItems.get("rultes");
        logger.info("ProxyIpPipeline {}", rultes );
        //todo 数据量大使用strem 并行流 待痛优化
        rultes.forEach(str->{
            //jsoup 解析判断Html会有问题  https://www.cnblogs.com/zhangyinhua/p/8037599.html
            String html="<html>  <body> <table> <tbody>"+str+"</tbody> </table> </body> </html>";
            Document document = Jsoup.parseBodyFragment(html);
            Element body = document.body();
            Elements elementsByTag = body.getElementsByTag("td");

            String ip = elementsByTag.get(0).text();
            String port= elementsByTag.get(1).text();
            String ad = elementsByTag.get(2).text();
            logger.info("获取TD标签下的内容 ip={} port={} ad={}",ip,port,ad);

            //TOdo 获取IP的信息 https://ip.taobao.com/service/getIpInfo.php?ip=27.192.200.105  https://ip.taobao.com/service/getIpInfo.php?ip=43.132.246.151

        });
    }
}
