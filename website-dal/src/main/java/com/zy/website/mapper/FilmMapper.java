package com.zy.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.website.model.FilmModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-24
 */
@Mapper
public interface FilmMapper extends BaseMapper<FilmModel> {


    @Select("select * from t_film where film_name = #{name} ")
    FilmModel getFilmByName(@Param("name") String name);

}
