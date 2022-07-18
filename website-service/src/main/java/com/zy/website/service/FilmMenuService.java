package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.facade.model.FilmMenuModel;
import com.zy.website.facade.response.MenuResponse;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-26
 */
public interface FilmMenuService extends IService<FilmMenuModel> {


    MenuResponse getTopMenu();

}
