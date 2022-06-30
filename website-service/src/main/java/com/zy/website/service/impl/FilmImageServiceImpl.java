package com.zy.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.facade.ApiReturn;
import com.zy.website.facade.code.ApiReturnCode;
import com.zy.website.facade.enums.FilmImageEnum;
import com.zy.website.facade.enums.WebsiteStatusEnum;
import com.zy.website.facade.model.FilmImageModel;
import com.zy.website.mapper.FilmImageMapper;
import com.zy.website.service.FilmImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhangyu
 * @since 2022-02-26
 */
@Service("filmImageService")
public class FilmImageServiceImpl extends ServiceImpl<FilmImageMapper, FilmImageModel> implements FilmImageService {

    @Resource
    FilmImageMapper filmImageMapper;

    @Override
    public ApiReturn getCarousel() {
        //轮播图 8张
        ApiReturn apiReturn = new ApiReturn();
        apiReturn.setCode(ApiReturnCode.SUCCESSFUL.getCode());
        apiReturn.setMsg(ApiReturnCode.SUCCESSFUL.getMessage());
        QueryWrapper<FilmImageModel> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FilmImageModel::getImgStatus, WebsiteStatusEnum.STATUS_MENU_Y.getCode())
                .eq(FilmImageModel::getImgType, FilmImageEnum.CAROUSEL.getCode())
                .orderByDesc(FilmImageModel::getCreactTime)
                .last("limit 8");
        List<FilmImageModel> filmImageModels = filmImageMapper.selectList(queryWrapper);
        if (filmImageModels == null) {
            apiReturn.setCode(ApiReturnCode.NO_DATA.getCode());
            apiReturn.setMsg(ApiReturnCode.NO_DATA.getMessage());
        }else {
            apiReturn.setData(filmImageModels);
        }
        return apiReturn;
    }
}
