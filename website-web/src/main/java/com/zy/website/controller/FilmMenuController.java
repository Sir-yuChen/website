package com.zy.website.controller;

import com.zy.website.response.MenuResponse;
import com.zy.website.service.FilmMenuService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 菜单 FilmMenuController
 * @author zhangyu
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/api/menu")
public class FilmMenuController extends BaseController {

    private static Logger logger = LogManager.getLogger(FilmMenuController.class);

    @Resource
    private FilmMenuService filmMenuService;

    /**
     * 菜单加载
     * @author zhangyu
     * @return com.zy.website.ApiReturn
     */
    @RequestMapping(value = "getTopMenu", method = RequestMethod.GET)
    public MenuResponse getTopMenu() {
        MenuResponse menuResponse = filmMenuService.getTopMenu();
        return menuResponse;
    }



}

