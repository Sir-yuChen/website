package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.ApiReturn;
import com.zy.website.dto.NoticeDTO;
import com.zy.website.model.FilmModel;
import com.zy.website.request.FilmSearchBarRequest;

import java.util.List;

/**
 * @author zhangyu
 * @since 2022-02-24
 */
public interface FilmService extends IService<FilmModel> {

    FilmModel getFilmByUid(String uid);

    List<NoticeDTO> videoChart(String typeCode);

    ApiReturn filmSearchBar(FilmSearchBarRequest filmSearchBarRequest);

}
