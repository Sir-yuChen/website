package com.zy.website.beanFactory;

import com.zy.website.facade.model.FilmMenuModel;
import com.zy.website.facade.model.FilmModel;
import com.zy.website.facade.model.dto.FilmInfoDTO;
import com.zy.website.facade.model.dto.MenuDTO;
import com.zy.website.utils.DateUtil;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @Author zhangyu
 * @Description
 * @Date 11:36 2021/12/28
 * @Param 继承Orika   Bean拷贝基础工厂，可直接在Service中Autowired MapperFacade使用拷贝功能
 * @return
 **/
@Component
public class MapperFacadeFactory implements FactoryBean<MapperFacade> {
    @Override
    public MapperFacade getObject() {
        return this.init().getMapperFacade();
    }

    @Override
    public Class<?> getObjectType() {
        return MapperFacade.class;
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

    private MapperFactory init() {
        MapperFactory factory = new DefaultMapperFactory.Builder().build();
        //处理特殊类型转换 自定义 exclude 排除的字段  field字段匹配
        factory.classMap(FilmMenuModel.class, MenuDTO.class).customize(new CustomMapper<FilmMenuModel, MenuDTO>() {
            //MappingContext context 必须   void mapAtoB(A var1, B var2, MappingContext var3);
            @Override
            public void mapAtoB(FilmMenuModel model, MenuDTO dto, MappingContext context) {
                dto.setCreactTime(DateUtil.format(model.getCreactTime(), DateUtil.YYYY_MM_DD)); //统一进行时间转换
            }
        }).byDefault().register();

        factory.classMap(FilmModel.class, FilmInfoDTO.class).customize(new CustomMapper<FilmModel, FilmInfoDTO>() {
            @Override
            public void mapAtoB(FilmModel model, FilmInfoDTO dto, MappingContext context) {
                dto.setCreactTime(DateUtil.format(model.getCreactTime(), DateUtil.YYYY_MM_DD)); //统一进行时间转换
                dto.setFilmPublishTime(DateUtil.format(model.getCreactTime(), DateUtil.YYYY_MM_DD)); //统一进行时间转换
            }
        }).byDefault().register();

        factory.classMap(com.ant.backstage.facade.ApiReturn.class, com.zy.website.facade.ApiReturn.class).customize(
                new CustomMapper<com.ant.backstage.facade.ApiReturn, com.zy.website.facade.ApiReturn>() {
                    @Override
                    public void mapAtoB(com.ant.backstage.facade.ApiReturn model, com.zy.website.facade.ApiReturn dto, MappingContext context) {
                        dto.setCode(model.getCode());
                        dto.setMsg(model.getMsg());
                        dto.setResultCode(model.getResultCode());
                        dto.setResultMsg(model.getResultMsg());
                        dto.setData(model.getData());
                    }
                }).byDefault().register();


        return factory;
    }

}
