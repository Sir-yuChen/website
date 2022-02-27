package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.dto.MenuDTO;
import com.zy.website.enums.MenuTypeEnum;
import com.zy.website.exception.WebsiteBusinessException;
import com.zy.website.mapper.FilmMenuMapper;
import com.zy.website.model.FilmMenuModel;
import com.zy.website.response.MenuResponse;
import com.zy.website.service.FilmMenuService;
import com.zy.website.utils.RedisUtil;
import com.zy.website.variable.Variable;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-26
 */
@Service("filmMenuService")
public class FilmMenuServiceImpl extends ServiceImpl<FilmMenuMapper, FilmMenuModel> implements FilmMenuService {

    private static Logger logger = LogManager.getLogger(FilmMenuServiceImpl.class);

    @Resource
    private FilmMenuMapper filmMenuMapper;

    @Resource
    RedisUtil redisUtil;

    @Resource
    MapperFacade  mapperFacade;

    @Override
    public MenuResponse getTopMenu() {
        MenuResponse menuResponse = new MenuResponse();
        menuResponse.setResultCode(ApiReturnCode.SUCCESSFUL.getCode());
        menuResponse.setResultMsg(ApiReturnCode.SUCCESSFUL.getMessage());

        String filmMenuKey= Variable.REDIS_MENU_KEY + MenuTypeEnum.MENU_FILM;
        String topMenuKey= Variable.REDIS_MENU_KEY + MenuTypeEnum.MENU_TOP;
        boolean b = redisUtil.hasKey(filmMenuKey);
        boolean b1 = redisUtil.hasKey(topMenuKey);
        if (b && b1) {
            List<MenuDTO> filmMenuDtos = (List<MenuDTO>) redisUtil.get(filmMenuKey);
            List<MenuDTO> topMenuDTOS = (List<MenuDTO>) redisUtil.get(filmMenuKey);
            menuResponse.setFilMenuList(filmMenuDtos);
            menuResponse.setTopList(topMenuDTOS);
            return menuResponse;
        }
        //条件构造器
        LambdaQueryWrapper<FilmMenuModel> query = null;
        //eq：等于，ne：不等于  gt：大于，ge：大于等于，lt：小于，le：小于等于
        // between、notBetween  isNull：字段 IS NULL，isNotNull：字段 IS NOT NULL
        //in：字段 IN (v0, v1, …)，notIn：字段 NOT IN (value.get(0), value.get(1), …)
        //or：或者 ，and[默认]：和   orderByAsc：升序：ORDER BY 字段, … ASC，orderByDesc：降序：ORDER BY 字段, … DESC
        query  =  new QueryWrapper<FilmMenuModel>().lambda().eq(FilmMenuModel::getMenuType, MenuTypeEnum.MENU_TOP.getCode());
        List<FilmMenuModel> filmMenuModels = filmMenuMapper.selectList(query);
        if (filmMenuModels == null || filmMenuModels.size() ==0) {
            logger.error("顶部菜单栏为配置！！！filmMenuModels={}",JSONObject.toJSONString(filmMenuModels));
            throw new WebsiteBusinessException(ApiReturnCode.HTTP_ERROR.getMessage(),ApiReturnCode.HTTP_ERROR.getCode());
        }
        List<MenuDTO> topMenuDTOS = new ArrayList<>();
        //查子类递归查询
        List<MenuDTO> menuDTOS = mapperFacade.mapAsList(filmMenuModels, MenuDTO.class);
        menuDTOS.forEach(dto->{
            if (dto.getMenuIsChild() && dto.getParentId() == 0) {
                topMenuDTOS.add(getMenuChild(dto,menuDTOS));
            }else if(dto.getParentId() == 0 && !dto.getMenuIsChild()){
                topMenuDTOS.add(dto);
            }
        });
        menuResponse.setTopList(topMenuDTOS);
        logger.info("顶部菜单集合：topMenuDTOS={}",JSONObject.toJSONString(topMenuDTOS));
        // 视频菜单
        query  =  new QueryWrapper<FilmMenuModel>().lambda().eq(FilmMenuModel::getMenuType, MenuTypeEnum.MENU_FILM.getCode());
        List<FilmMenuModel> filmMenus = filmMenuMapper.selectList(query);
        List<MenuDTO> filmMenusDto = mapperFacade.mapAsList(filmMenus, MenuDTO.class);
        List<MenuDTO> filmMenusDTOS = new ArrayList<>();
        filmMenusDto.stream().filter(dto-> dto.getParentId() == 0).forEach(dto->{
            if (dto.getMenuIsChild()) {
                filmMenusDTOS.add(getMenuChild(dto,filmMenusDto));
            }else{
                filmMenusDTOS.add(dto);
            }
        });
        menuResponse.setFilMenuList(filmMenusDTOS);
        logger.info("首页视频菜单集合：filmMenusDTOS={}",JSONObject.toJSONString(filmMenusDTOS));
        // redis 缓存
        redisUtil.set(filmMenuKey,filmMenusDTOS);
        redisUtil.set(topMenuKey,topMenuDTOS);
        return menuResponse;
    }

    public MenuDTO getMenuChild(MenuDTO menu,List<MenuDTO> filmMenuModels){
        filmMenuModels.forEach(item->{
            if (menu.getId() == item.getParentId()) {
                if (menu.getMenuIsChild() && menu.getChildList() == null ) {
                    menu.setChildList(new ArrayList<MenuDTO>());
                }
                menu.getChildList().add(getMenuChild(item,filmMenuModels));
            }
        });
        return menu;
    }


}
