package com.zy.website.service.impl;

import com.zy.website.model.FilmModel;
import com.zy.website.mapper.FilmMapper;
import com.zy.website.service.FilmService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-24
 */
@Service("filmService")
public class FilmServiceImpl extends ServiceImpl<FilmMapper, FilmModel> implements FilmService {


    @Resource
    FilmMapper filmMapper;

    @Override
    public FilmModel getFilmByName(String name) {

        return filmMapper.getFilmByName(name);
    }
}
