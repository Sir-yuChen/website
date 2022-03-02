package com.zy.website.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zy.website.model.FilmModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

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


    @Select("select * from t_film where film_uid = #{uid} ")
    FilmModel getFilmByUid(@Param("uid") String uid);

    @Select("SELECT * FROM t_film WHERE film_status='Y' AND film_genre =#{mark} AND film_play_count>=1000 ORDER BY creact_time DESC ,film_play_count DESC")
    List<FilmModel> selectFrontList(@Param("mark") String mark);

}
