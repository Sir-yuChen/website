package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.dto.NoticeDTO;
import com.zy.website.enums.FilmLeaderboardEnum;
import com.zy.website.exception.WebsiteBusinessException;
import com.zy.website.mapper.FilmMapper;
import com.zy.website.model.FilmModel;
import com.zy.website.service.FilmService;
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
 * @since 2022-02-24
 */
@Service("filmService")
public class FilmServiceImpl extends ServiceImpl<FilmMapper, FilmModel> implements FilmService {

    private static Logger logger = LogManager.getLogger(FilmServiceImpl.class);

    @Resource
    FilmMapper filmMapper;


    @Override
    public FilmModel getFilmByUid(String uid) {
        return filmMapper.getFilmByUid(uid);
    }

    @Override
    public List<NoticeDTO> videoChart(String typeCode) {
        // 视频榜
        FilmLeaderboardEnum byCode = FilmLeaderboardEnum.getByCode(typeCode);
        if (byCode == null) {
            logger.error("枚举值获取异常 typeCode={}", typeCode);
            throw new WebsiteBusinessException(ApiReturnCode.HTTP_ERROR.getMessage(), ApiReturnCode.HTTP_ERROR.getCode());
        }
        List<NoticeDTO> noticeDTOS = new ArrayList<>();
        switch (byCode) {
            case LEADERBOARD_ALL_NOTICE:   //所有榜单
                // todo 所有榜单数据

                break;
            case LEADERBOARD_FILM_NOTICE:   //电影总榜
                NoticeDTO dto = this.getFilmNoticeData(typeCode);
                dto.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getCode());
                dto.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getDesc());
                noticeDTOS.add(dto);
                logger.info("电影总榜 数据集合 noticeDTOS={}", JSONObject.toJSONString(noticeDTOS));
                break;
        }

        return noticeDTOS;
    }
    //榜单数据处理方法
    private NoticeDTO getFilmNoticeData(String typeCode) {
        // 视频为电影 播放量前20
        QueryWrapper<FilmModel> query = new QueryWrapper<>();
        query.lambda().eq(FilmModel::getFilmGenre,typeCode).orderByAsc(FilmModel::getFilmPlayCount).last("limit 10");
        List<FilmModel> filmModels = filmMapper.selectList(query);
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeData(filmModels);
        return noticeDTO;
    }















}
