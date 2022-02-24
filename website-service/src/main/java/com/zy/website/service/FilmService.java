package com.zy.website.service;

import com.zy.website.model.FilmModel;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-24
 */
public interface FilmService extends IService<FilmModel> {

    FilmModel getFilmByName(String name);

}
