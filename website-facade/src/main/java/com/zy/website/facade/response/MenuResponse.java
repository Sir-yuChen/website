package com.zy.website.facade.response;

import com.zy.website.facade.model.dto.MenuDTO;
import lombok.Data;

import java.util.List;

/*
 * 菜单 response
 * @author zhangyu
 * @date 2022/2/26 17:16
 * @return
 */
@Data
public class MenuResponse extends FrontResponse{

    /**
     * 顶部菜单集合
     * @mock
     * @since v1.0
     */
    private List<MenuDTO> topList;

    /**
     * 首页视频菜单集合
     * @mock
     * @since v1.0
     */
    private List<MenuDTO> filMenuList;


}
