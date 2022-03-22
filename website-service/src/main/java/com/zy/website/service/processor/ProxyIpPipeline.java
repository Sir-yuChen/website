package com.zy.website.service.processor;

import com.alibaba.fastjson.JSONObject;
import com.zy.website.mapper.ProxyIpMapper;
import com.zy.website.model.ProxyIpModel;
import com.zy.website.model.dto.IpJsonDTO;
import com.zy.website.utils.NetStateUtil;
import com.zy.website.utils.RestTemplateUtils;
import com.zy.website.utils.spring.ApplicationContextUtil;
import ma.glasnost.orika.MapperFacade;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * 用于将XXX html页面解析出的数据存储到mysql数据库
 **/
@Component("proxyIpPipeline")
public class ProxyIpPipeline implements Pipeline {

    private static Logger logger = LogManager.getLogger(ProxyIpPipeline.class);

    @Override
    public void process(ResultItems resultItems, Task task) {
        List<String> rultes = (List<String>) resultItems.get("rultes");
        logger.info("ProxyIpPipeline 共获取数据{}条", rultes.size());
        Stream<String> stringStream = rultes.parallelStream(); //第三方接口调用过快无法获取到IP正确的信息  部分可用IP无法入库
        stringStream.forEach(str -> {
            logger.info("当前线程名: " + Thread.currentThread().getName());
            //jsoup 解析判断Html会有问题  https://www.cnblogs.com/zhangyinhua/p/8037599.html
            String html = "<html>  <body> <table> <tbody>" + str + "</tbody> </table> </body> </html>";
            Document document = Jsoup.parseBodyFragment(html);
            Element body = document.body();
            Elements elementsByTag = body.getElementsByTag("td");

            String ip = elementsByTag.get(0).text();
            String port = elementsByTag.get(1).text();
            String type = elementsByTag.get(3).text();
            logger.info("获取TD标签下的内容 ip={} port={}", ip, port);
            //校验IP 是否可用
            boolean b = NetStateUtil.connectingAddress(ip);
            if (b) {
                try {
                    RestTemplateUtils restTemplateUtils = ApplicationContextUtil.getBean("restTemplateUtils", RestTemplateUtils.class);
                    List<String> params = new ArrayList<>();
                    params.add(ip);
                    //2. 获取IP信息
                    JSONObject jsonObject = restTemplateUtils.httpGetPlaceholder("http://ip-api.com/json", params, null, JSONObject.class);
                    if (jsonObject != null) {
                        MapperFacade mapperFacade = ApplicationContextUtil.getBean(MapperFacade.class);
                        ProxyIpMapper proxyIpMapper = ApplicationContextUtil.getBean(ProxyIpMapper.class);
                        logger.info("API 获取IP详情  ip={} jsonObject={}", ip, JSONObject.toJSONString(jsonObject));
                        IpJsonDTO ipJsonDTO = JSONObject.parseObject(jsonObject.toJSONString(), IpJsonDTO.class);
                        ProxyIpModel proxyIpModel = mapperFacade.map(ipJsonDTO, ProxyIpModel.class);
                        proxyIpModel.setIpAddress(ip);
                        proxyIpModel.setPlatformAddress("www.66ip.cn");
                        proxyIpModel.setIpType(type);
                        proxyIpModel.setIpStatus("Y");
                        proxyIpModel.setValidateCount(1);
                        proxyIpModel.setValidateDate(new Date());
                        proxyIpModel.setPort(Long.valueOf(port));
                        proxyIpMapper.insert(proxyIpModel);
                        logger.info("IP PROXY 持久化成功{}", JSONObject.toJSONString(proxyIpModel));
                    }
                } catch (NumberFormatException e) {
                    logger.info("IP PROXY 持久化处理异常{}", e);
                }
            }

        });
    }
}
