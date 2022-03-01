package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.ApiReturn;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.dto.NoticeDTO;
import com.zy.website.enums.FilmLeaderboardEnum;
import com.zy.website.mapper.FilmMapper;
import com.zy.website.mapper.PersonInfoMapper;
import com.zy.website.model.FilmModel;
import com.zy.website.model.PersonInfoModel;
import com.zy.website.request.FilmSearchBarRequest;
import com.zy.website.response.FilmSearchBarResponse;
import com.zy.website.service.FilmService;
import com.zy.website.utils.RedisUtil;
import com.zy.website.variable.Variable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * 服务实现类
 * @author zhangyu
 * @since 2022-02-24
 */
@Service("filmService")
public class FilmServiceImpl extends ServiceImpl<FilmMapper, FilmModel> implements FilmService {

    private static Logger logger = LogManager.getLogger(FilmServiceImpl.class);

    @Resource
    FilmMapper filmMapper;

    @Resource
    PersonInfoMapper personInfoMapper;

    @Resource
    RedisUtil redisUtil;

    @Override
    public FilmModel getFilmByUid(String uid) {
        return filmMapper.getFilmByUid(uid);
    }

    @Override
    public List<NoticeDTO> videoChart(String typeCode) {
        List<NoticeDTO> noticeDTOS = new ArrayList<>();
        String[] split = typeCode.trim().split(",");
        Arrays.stream(split).forEach(code -> {
            FilmLeaderboardEnum byCode = FilmLeaderboardEnum.getByCode(code);
            if (byCode == null) {
                logger.error("枚举值获取异常 未定义 code={}", code);
//                throw new WebsiteBusinessException(ApiReturnCode.HTTP_ERROR.getMessage(), ApiReturnCode.HTTP_ERROR.getCode());
                return;
            }
            switch (byCode) {
                case LEADERBOARD_ALL_NOTICE:   //所有榜单
                    //  所有榜单数据  使用异步执行多个方法
                    try {
                        CompletableFuture<NoticeDTO> filmDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> varietyDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_VARIETY_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> cartoonDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_CARTOON_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> usdramaDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_USDRAMA_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> hitDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_HIT_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> scoreDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_SCORE_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> episodeDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_EPISODE_NOTICE.getCode()));
                        CompletableFuture.allOf(filmDto, varietyDto, cartoonDto, usdramaDto, hitDto, scoreDto, episodeDto).join();
                        noticeDTOS.add(filmDto.get());
                        noticeDTOS.add(varietyDto.get());
                        noticeDTOS.add(cartoonDto.get());
                        noticeDTOS.add(usdramaDto.get());
                        noticeDTOS.add(hitDto.get());
                        noticeDTOS.add(scoreDto.get());
                        noticeDTOS.add(episodeDto.get());
                        logger.info("所有视频榜单数据获取 数据集合 noticeDTOS={}", JSONObject.toJSONString(noticeDTOS));
                    } catch (Exception e) {
                        logger.error("所有视频榜单数据获取异常", e);
                    }
                    break;
                case LEADERBOARD_FILM_NOTICE:   //电影总榜
                    NoticeDTO filmDto = this.getFilmNoticeData(Variable.REDIS_NOTICES_KEY, code);
                    noticeDTOS.add(filmDto);
                    logger.info("电影总榜 数据集合 noticeDTOS={},filmDto={}",
                            JSONObject.toJSONString(noticeDTOS), JSONObject.toJSONString(filmDto));
                    break;
                case LEADERBOARD_VARIETY_NOTICE:   //综艺总榜
                    NoticeDTO varietyDto = this.getFilmNoticeData(Variable.REDIS_NOTICES_KEY, code);
                    noticeDTOS.add(varietyDto);
                    logger.info("综艺总榜 数据集合 noticeDTOS={},varietyDto={}",
                            JSONObject.toJSONString(noticeDTOS), JSONObject.toJSONString(varietyDto));
                    break;
                case LEADERBOARD_CARTOON_NOTICE:   //动漫总榜
                    NoticeDTO cartoonDto = this.getFilmNoticeData(Variable.REDIS_NOTICES_KEY, code);
                    noticeDTOS.add(cartoonDto);
                    logger.info("动漫总榜 数据集合 noticeDTOS={},cartoonDto={}",
                            JSONObject.toJSONString(noticeDTOS), JSONObject.toJSONString(cartoonDto));
                    break;
                case LEADERBOARD_USDRAMA_NOTICE:   //美剧总榜
                    NoticeDTO usdramaDto = this.getFilmNoticeData(Variable.REDIS_NOTICES_KEY, code);
                    noticeDTOS.add(usdramaDto);
                    logger.info("美剧总榜 数据集合 noticeDTOS={},usdramaDto={}",
                            JSONObject.toJSONString(noticeDTOS), JSONObject.toJSONString(usdramaDto));
                    break;
                case LEADERBOARD_HIT_NOTICE:   //热播榜
                    NoticeDTO hitDto = this.getFilmNoticeData(Variable.REDIS_NOTICES_KEY, code);
                    noticeDTOS.add(hitDto);
                    logger.info("热播榜 数据集合 noticeDTOS={},hitDto={}",
                            JSONObject.toJSONString(noticeDTOS), JSONObject.toJSONString(hitDto));
                    break;
                case LEADERBOARD_SCORE_NOTICE:   //高分榜
                    NoticeDTO scoreDto = this.getFilmNoticeData(Variable.REDIS_NOTICES_KEY, code);
                    noticeDTOS.add(scoreDto);
                    logger.info("高分榜 数据集合 noticeDTOS={},scoreDto={}",
                            JSONObject.toJSONString(noticeDTOS), JSONObject.toJSONString(scoreDto));
                    break;
                case LEADERBOARD_EPISODE_NOTICE:   //剧集总榜
                    NoticeDTO episodeDto = this.getFilmNoticeData(Variable.REDIS_NOTICES_KEY, code);
                    noticeDTOS.add(episodeDto);
                    logger.info("剧集总榜 数据集合 noticeDTOS={},episodeDto={}",
                            JSONObject.toJSONString(noticeDTOS), JSONObject.toJSONString(episodeDto));
                    break;
            }
        });
        return noticeDTOS;
    }

    @Override
    public ApiReturn filmSearchBar(FilmSearchBarRequest filmSearchBarRequest) {
        ApiReturn apiReturn = new ApiReturn();
        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        // TODO 加入ES 关键词搜索 高亮 【es 应一个完整的视频信息】
        if (Optional.ofNullable(filmSearchBarRequest.getSearchWord()).isPresent()) {
            List<FilmSearchBarResponse> data = new ArrayList<>();
            //电影
            Page<FilmModel> filmPage = new Page<>(filmSearchBarRequest.getPageNum(), filmSearchBarRequest.getPageSize());   //查询第pageNum页，每页pageSize条数据
            //将分页参数page作为Mybatis或Mybatis Plus的第一个参数传入持久层函数，即可完成分页查询
            QueryWrapper<FilmModel> query = new QueryWrapper<>();
            query.lambda()
                    .like(FilmModel::getFilmName, filmSearchBarRequest.getSearchWord())
                    .or()
                    .like(FilmModel::getFilmOriginalName, filmSearchBarRequest.getSearchWord());
            if (filmSearchBarRequest.getTotal() == 0 || filmSearchBarRequest.getPageNum() < filmSearchBarRequest.getTotal() % filmSearchBarRequest.getPageSize()) {
                FilmSearchBarResponse filmResponse = new FilmSearchBarResponse();
                Page<FilmModel> filmModelPage = filmMapper.selectPage(filmPage, query);
                filmResponse.setResultList(filmModelPage.getRecords());
                filmResponse.setPageSize(filmModelPage.getSize());
                filmResponse.setPageNum(filmModelPage.getCurrent());
                filmResponse.setTotal(filmModelPage.getTotal());
                data.add(filmResponse);
            }
            //明星
            Page<PersonInfoModel> personPage = new Page<>(filmSearchBarRequest.getPageNum(), filmSearchBarRequest.getPageSize());   //查询第pageNum页，每页pageSize条数据
            QueryWrapper<PersonInfoModel> queryPerson = new QueryWrapper<>();
            queryPerson.lambda()
                    .like(PersonInfoModel::getChName, filmSearchBarRequest.getSearchWord())
                    .or()
                    .like(PersonInfoModel::getEnName, filmSearchBarRequest.getSearchWord())
                    .groupBy(PersonInfoModel::getFilmUid);
            if (filmSearchBarRequest.getTotal() == 0 || filmSearchBarRequest.getPageNum() < filmSearchBarRequest.getTotal() % filmSearchBarRequest.getPageSize()) {
                Page<PersonInfoModel> personInfoModelPage = personInfoMapper.selectPage(personPage, queryPerson);
                List<PersonInfoModel> records = personInfoModelPage.getRecords();
                List<FilmModel> filmModels = new ArrayList<>();
                records.forEach(item -> filmModels.add(filmMapper.getFilmByUid(item.getFilmUid())));
                FilmSearchBarResponse personResponse = new FilmSearchBarResponse();
                personResponse.setResultList(filmModels);
                personResponse.setPageSize(personInfoModelPage.getSize());
                personResponse.setPageNum(personInfoModelPage.getCurrent());
                personResponse.setTotal(personInfoModelPage.getTotal());
                data.add(personResponse);
            }
            logger.info("搜索框 搜索结果集合 data={}", JSONObject.toJSONString(data));
            apiReturn.setData(data);
        } else {
            logger.error("搜索框关键字为空,filmSearchBarRequest={}",
                    JSONObject.toJSONString(filmSearchBarRequest));
            apiReturn.setCode(ApiReturnCode.PARAMS_ERROR.getCode());
            apiReturn.setMsg(ApiReturnCode.PARAMS_ERROR.getCode());
        }
        return apiReturn;
    }

    //榜单数据处理方法
    //1. 电影排行榜  FILM_NOTICE
    private NoticeDTO getFilmNoticeData(String redisKey, String typeCode) {
        List<FilmModel> redisNotice = this.getRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getCode());
        noticeDTO.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getDesc());
        noticeDTO.setNoticeOrder(FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        if (redisNotice != null && redisNotice.size() > 0) {
            noticeDTO.setNoticeData(redisNotice);
            return noticeDTO;
        }
        // 视频为电影 播放量前20
        List<FilmModel> filmModels = this.selectSqlData(typeCode, 20);
        noticeDTO.setNoticeData(filmModels);
        this.setRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder(), filmModels);
        return noticeDTO;
    }

    //2. 综艺总榜   VARIETY_NOTICE
    private NoticeDTO getVarietyNoticeData(String redisKey, String typeCode) {
        List<FilmModel> redisNotice = this.getRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        // 视频为综艺 播放量前20
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_VARIETY_NOTICE.getCode());
        noticeDTO.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_VARIETY_NOTICE.getDesc());
        noticeDTO.setNoticeOrder(FilmLeaderboardEnum.LEADERBOARD_VARIETY_NOTICE.getOrder());
        if (redisNotice != null && redisNotice.size() > 0) {
            noticeDTO.setNoticeData(redisNotice);
            return noticeDTO;
        }
        List<FilmModel> filmModels = this.selectSqlData(typeCode, 20);
        noticeDTO.setNoticeData(filmModels);
        this.setRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_VARIETY_NOTICE.getOrder(), filmModels);
        return noticeDTO;
    }

    //3. 动漫总榜   CARTOON_NOTICE
    private NoticeDTO getCartoonNoticeData(String redisKey, String typeCode) {
        List<FilmModel> redisNotice = this.getRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        // 动漫 播放量前20
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_CARTOON_NOTICE.getCode());
        noticeDTO.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_CARTOON_NOTICE.getDesc());
        noticeDTO.setNoticeOrder(FilmLeaderboardEnum.LEADERBOARD_CARTOON_NOTICE.getOrder());
        if (redisNotice != null && redisNotice.size() > 0) {
            noticeDTO.setNoticeData(redisNotice);
            return noticeDTO;
        }
        List<FilmModel> filmModels = this.selectSqlData(typeCode, 20);
        noticeDTO.setNoticeData(filmModels);
        this.setRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_CARTOON_NOTICE.getOrder(), filmModels);
        return noticeDTO;
    }

    //4. 美剧总榜   USDRAMA_NOTICE
    private NoticeDTO getUsdramaNoticeData(String redisKey, String typeCode) {
        List<FilmModel> redisNotice = this.getRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_USDRAMA_NOTICE.getCode());
        noticeDTO.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_USDRAMA_NOTICE.getDesc());
        noticeDTO.setNoticeOrder(FilmLeaderboardEnum.LEADERBOARD_USDRAMA_NOTICE.getOrder());
        if (redisNotice != null && redisNotice.size() > 0) {
            noticeDTO.setNoticeData(redisNotice);
            return noticeDTO;
        }
        List<FilmModel> filmModels = this.selectSqlData(typeCode, 20);
        noticeDTO.setNoticeData(filmModels);
        this.setRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_USDRAMA_NOTICE.getOrder(), filmModels);
        return noticeDTO;
    }

    //5. 热播榜   HIT_NOTICE
    private NoticeDTO getHitNoticeData(String redisKey, String typeCode) {
        List<FilmModel> redisNotice = this.getRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_HIT_NOTICE.getCode());
        noticeDTO.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_HIT_NOTICE.getDesc());
        noticeDTO.setNoticeOrder(FilmLeaderboardEnum.LEADERBOARD_HIT_NOTICE.getOrder());
        if (redisNotice != null && redisNotice.size() > 0) {
            noticeDTO.setNoticeData(redisNotice);
            return noticeDTO;
        }
        List<FilmModel> filmModels = this.selectSqlData(typeCode, 20);
        noticeDTO.setNoticeData(filmModels);
        this.setRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_HIT_NOTICE.getOrder(), filmModels);
        return noticeDTO;
    }

    //6. 高分榜   LEADERBOARD_SCORE_NOTICE
    private NoticeDTO getScoreNoticeData(String redisKey, String typeCode) {
        List<FilmModel> redisNotice = this.getRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_SCORE_NOTICE.getCode());
        noticeDTO.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_SCORE_NOTICE.getDesc());
        noticeDTO.setNoticeOrder(FilmLeaderboardEnum.LEADERBOARD_SCORE_NOTICE.getOrder());
        if (redisNotice != null && redisNotice.size() > 0) {
            noticeDTO.setNoticeData(redisNotice);
            return noticeDTO;
        }
        List<FilmModel> filmModels = this.selectSqlData(typeCode, 20);
        noticeDTO.setNoticeData(filmModels);
        this.setRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_SCORE_NOTICE.getOrder(), filmModels);
        return noticeDTO;
    }

    //6. 剧集总榜   LEADERBOARD_EPISODE_NOTICE
    private NoticeDTO getEpisodeNoticeData(String redisKey, String typeCode) {
        List<FilmModel> redisNotice = this.getRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder());
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setNoticeMark(FilmLeaderboardEnum.LEADERBOARD_EPISODE_NOTICE.getCode());
        noticeDTO.setNoticeName(FilmLeaderboardEnum.LEADERBOARD_EPISODE_NOTICE.getDesc());
        noticeDTO.setNoticeOrder(FilmLeaderboardEnum.LEADERBOARD_EPISODE_NOTICE.getOrder());
        if (redisNotice != null && redisNotice.size() > 0) {
            noticeDTO.setNoticeData(redisNotice);
            return noticeDTO;
        }
        List<FilmModel> filmModels = this.selectSqlData(typeCode, 20);
        noticeDTO.setNoticeData(filmModels);
        this.setRedisNotice(redisKey, typeCode, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getOrder(), filmModels);
        return noticeDTO;
    }


    private List<FilmModel> selectSqlData(String typeCode, Integer number) {
        QueryWrapper<FilmModel> query = new QueryWrapper<>();
        // TODO 大数据了优化sql 条查 排序
        query.lambda().eq(FilmModel::getFilmGenre, typeCode).orderByAsc(FilmModel::getFilmPlayCount).last("limit " + number);
        List<FilmModel> filmModels = filmMapper.selectList(query);
        return filmModels;
    }

    //获取缓存中的排行榜信息
    private List<FilmModel> getRedisNotice(String redisKey, String typeCode, Integer order) {
        boolean b = redisUtil.hHasKey(redisKey, typeCode + "_" + order);
        if (!b) {
            logger.info("缓存中暂无当前排行榜数据 rediskey={},typeCode={},order={}", redisKey, typeCode, order);
            return null;
        }
        List<FilmModel> list = (List<FilmModel>) redisUtil.hget(redisKey, typeCode + "_" + order);
        return list;
    }

    //获取缓存中的排行榜信息
    private Boolean setRedisNotice(String redisKey, String typeCode, Integer order, List<FilmModel> filmModels) {
        boolean hset = redisUtil.hset(redisKey, typeCode + "_" + order, filmModels, 60 * 60 * 24);
        if (!hset) {
            logger.error("排行榜信息存储redis失败,rediskey={},typeCode={},order={},filmModels={}",
                    redisKey, typeCode, order, JSONObject.toJSONString(filmModels));
        }
        return hset;
    }

}
