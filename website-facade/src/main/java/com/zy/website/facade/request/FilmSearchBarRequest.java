package com.zy.website.facade.request;

import lombok.Data;

/**
 * @author zhangyu
 * @description  搜索框请求入参
 * @since v1.0
 **/
@Data
public class FilmSearchBarRequest extends FrontRequest {

    /***
      *  明星名/电影名
      * @mock 弗兰克·达拉邦特/肖申克的救赎
      * @since
      */
    private String searchWord;

}
