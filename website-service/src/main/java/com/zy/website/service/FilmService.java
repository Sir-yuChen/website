package com.zy.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zy.website.ApiReturn;
import com.zy.website.model.FilmModel;
import com.zy.website.model.dto.NoticeDTO;
import com.zy.website.request.FilmSearchBarRequest;
import com.zy.website.response.TopFilmResponse;

import java.util.List;

/**
 * @author zhangyu
 * @since 2022-02-24
 */
public interface FilmService extends IService<FilmModel> {

    FilmModel getFilmByUid(String uid);

    List<NoticeDTO> videoChart(String typeCode);

    ApiReturn filmSearchBar(FilmSearchBarRequest filmSearchBarRequest);

    TopFilmResponse frontPageFilm();

    ApiReturn refreshFilmData() throws Exception;

    ApiReturn refreshTop250FilmData() ;

    void getFilmInfoByExternalApi(String filmKeyWord) ;


}
