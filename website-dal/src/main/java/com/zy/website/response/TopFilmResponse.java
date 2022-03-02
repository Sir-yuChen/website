package com.zy.website.response;

import com.zy.website.dto.MenuDTO;
import lombok.Data;

import java.util.List;

/*
 * 首页菜单类型 视频展示 response
 * @author zhangyu
 * @date 2022/2/26 17:16
 * @return
 */
@Data
public class TopFilmResponse extends FrontResponse{

    /***
     * 首页类型展示视频集合
     */
    private List<MenuDTO> topFimlList;

}