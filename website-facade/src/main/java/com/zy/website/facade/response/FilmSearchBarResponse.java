package com.zy.website.facade.response;

import com.zy.website.facade.model.FilmModel;
import lombok.Data;

import java.util.List;

/**
 * 搜索框 response
 * @author zhangyu
 * @description
 * @date 2022/2/28 16:01
 */
@Data
public class FilmSearchBarResponse extends PageResponse {

    /**
     * 搜索结果
     * @mock
     * @since v1.0
     */
    private List<FilmModel> resultList;


}
