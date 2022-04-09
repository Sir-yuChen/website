package com.zy.website.service;

import com.zy.website.ApiReturn;
import com.zy.website.model.FilmImageModel;
import com.baomidou.mybatisplus.extension.service.IService;

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
