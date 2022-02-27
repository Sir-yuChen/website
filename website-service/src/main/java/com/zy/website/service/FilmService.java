package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.dto.NoticeDTO;
import com.zy.website.model.FilmModel;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-24
 */
public interface FilmService extends IService<FilmModel> {

    FilmModel getFilmByUid(String uid);

    List<NoticeDTO> videoChart(String typeCode);

}
