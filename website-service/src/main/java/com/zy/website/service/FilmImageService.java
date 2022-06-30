package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.model.FilmImageModel;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-26
 */
public interface FilmImageService extends IService<FilmImageModel> {

    ApiReturn getCarousel();

}
