package com.zy.website.controller;

import com.zy.website.model.FilmModel;
import com.zy.website.service.FilmService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 视频 FilmController
 * @author zhangyu
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/film-model")
public class FilmController extends BaseController {

    private static Logger logger = LogManager.getLogger(FilmController.class);

    @Resource
    FilmService filmService;
    //https://smart-doc-group.github.io/#/zh-cn/start/javadoc?id=_11-param-%e7%89%b9%e6%ae%8a%e7%94%a8%e6%b3%95  官网smart-doc

    //@deprecated 弃用注解 smart-doc
    /**
     * 视频详情[条查分页]
     * @author zhangyu
     * @param name 视频名称|肖申克的救赎
     * @description  根据视频名称获取视频详情
     * @date 2022/2/25 10:35
     * @return com.zy.website.model.FilmModel
     */
    @RequestMapping(value = "getFilm", method = RequestMethod.GET)
    public FilmModel getFilmByName(@RequestParam String name) {
        FilmModel filmModel = filmService.getFilmByName(name);
        logger.info("获取视频信息:filModel={}",filmModel);
        return filmModel;
    }

}

