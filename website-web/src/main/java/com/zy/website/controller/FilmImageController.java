package com.zy.website.controller;


import com.zy.website.ApiReturn;
import com.zy.website.service.FilmImageService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 图片 FilmImageController
 *
 * @author zhangyu
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/filmImage")
public class FilmImageController extends BaseController {
    private static Logger logger = LogManager.getLogger(PlayRecordController.class);

    @Resource
    FilmImageService filmImageService;

    /**
     * 获取首页轮播图
     *
     * @return com.zy.website.ApiReturn
     * @author zhangyu
     * @date 2022/4/9 11:23
     */
    @RequestMapping(value = "carousel", method = RequestMethod.GET)
    public ApiReturn getCarousel() {
        ApiReturn apiReturn = filmImageService.getCarousel();
        return apiReturn;
    }

}

