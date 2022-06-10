package com.zy.website.test.webmagic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2022/4/16 20:45
 **/

public class FilmMagic implements PageProcessor {
    private Site site = Site.me().setRetryTimes(3).setSleepTime(1);

    private List infoUrlList = new ArrayList();
    private List reList;
    private Integer total = 0;
    private static Whitelist whitelist = new Whitelist();

    public void process(Page page) {
        System.out.println("开始========================================================>");
        //用于获取所有满足这个正则表达式的链接
        List<String> links = page.getHtml().links().regex("http://www\\.czspp\\.com/movie_bt").all();
        //将这些链接加入到待抓取的队列中去
        List<String> collect = links.stream().distinct().collect(Collectors.toList());

        String xpath = "//div[@class='pagenavi_txt']//a[@class='extend']/@href";
        String pagesTotal = page.getHtml().xpath(xpath).get();
        if (pagesTotal != null && !pagesTotal.endsWith("/")) {
            Integer number = Integer.valueOf(pagesTotal.substring(pagesTotal.lastIndexOf("/") + 1));
            for (Integer i = 2; i < number; i++) {
                total = i;
                collect.add("http" + pagesTotal.substring(5, pagesTotal.lastIndexOf("/") + 1) + i);
            }
        }
        page.addTargetRequests(collect);
        List<String> urlList = page.getHtml().xpath("//div[@class='bt_img mi_ne_kd mrb']//h3[@class='dytit']/a/@href").all();
        if (!CollectionUtils.isEmpty(urlList)) {
            urlList.forEach(u->infoUrlList.add("http" + u.substring(5)));
            String url = page.getRequest().getUrl();
            if (url != null && !url.endsWith("/") && !url.endsWith("movie_bt")) {
                Integer value = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1));
                System.out.println("value==>" + value + " total==>" + total);
                if (value.equals(total)) {
                    page.addTargetRequests(infoUrlList);
                }
            }
        }
//        System.out.println("infoUrlList = " + infoUrlList);
        String infoStr = page.getHtml().xpath("//div[@class='mikd']//div[@class='mi_cont']//div[@class='dyxingq']//div[@class='mi_ne_kd dypre']").get();
        if (Optional.ofNullable(infoStr).isPresent()) {
            String html = "<html>  <body> <table> <tbody>" + infoStr + "</tbody> </table> </body> </html>";
            Document document = Jsoup.parseBodyFragment(html);
            Element body = document.body();
            String imgurl = body.getElementsByClass("dyimg fl").get(0).getElementsByTag("img").get(0).attr("src");
            System.out.println("imgurl ===> " + imgurl);
            Element element = body.getElementsByClass("dytext fl").get(0);
            String filmName = element.getElementsByClass("moviedteail_tt").get(0).getElementsByTag("h1").get(0).text();
            System.out.println("filmName ===> " + filmName);
            Elements elementsByTag = element.getElementsByClass("moviedteail_list").get(0).getElementsByTag("li");
            elementsByTag.forEach(item -> {
                String text = item.text().substring(0,item.text().indexOf("："));
                System.out.println( "type ===>" +text);
                Elements a = item.getElementsByTag("a");
                List<String> list = new ArrayList<>();
                a.forEach(aTag -> list.add(aTag.text()));
                if (CollectionUtils.isEmpty(list)) {
                    Elements span = item.getElementsByTag("span");
                    span.forEach(spanTag -> list.add(spanTag.text()));
                }
                System.out.println("value ===> " + list);
            });
        }
        String str = page.getHtml().xpath("//div[@class='mikd']//div[@class='mi_cont']//div[@class='mi_ne_kd']//div[@class='yp_context']").get();
        if (Optional.ofNullable(str).isPresent()) {
            String html = "<html>  <body> <table> <tbody>" + str + "</tbody> </table> </body> </html>";
            Document document = Jsoup.parseBodyFragment(html);
            Element body = document.body();
            String synopsis = body.getElementsByClass("yp_context").get(0).text();
            String synopsisText = synopsis.indexOf("<") > -1 && synopsis.indexOf(">") > -1 ? Jsoup.clean(synopsis, whitelist) : synopsis;
            System.out.println("synopsis ===> " + synopsisText);
        }

    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        /*Spider.create(new FilmMagic())
                .addUrl("http://www.czspp.com/movie_bt")
                .run();*/
        BigDecimal bigDecimal = new BigDecimal(0);
        BigDecimal bigDecimal1 = new BigDecimal(3);
        BigDecimal add = bigDecimal.add(bigDecimal1);
        System.out.println("bigDecimal1 = " + bigDecimal);


    }

}
