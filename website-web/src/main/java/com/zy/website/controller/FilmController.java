package com.zy.website.controller;

import com.alibaba.fastjson.JSONObject;
import com.zy.website.ApiReturn;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.model.FilmModel;
import com.zy.website.model.dto.NoticeDTO;
import com.zy.website.request.FilmSearchBarRequest;
import com.zy.website.response.NoticeResponse;
import com.zy.website.response.TopFilmResponse;
import com.zy.website.service.FilmService;
import com.zy.website.service.impl.FileService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 视频 FilmController
 *
 * @author zhangyu
 * @since 2022-02-24
 */
@RestController
@RequestMapping("/api/film")
public class FilmController extends BaseController {

    private static Logger logger = LogManager.getLogger(FilmController.class);

    @Resource
    FilmService filmService;
    //https://smart-doc-group.github.io/#/zh-cn/start/javadoc?id=_11-param-%e7%89%b9%e6%ae%8a%e7%94%a8%e6%b3%95  官网smart-doc
    //@deprecated 弃用注解 smart-doc
    @Resource
    FileService fileService;

    /**
     * 视频详情[单查]
     *
     * @param uid 视频唯一UID|5f968bfcee3680299115bbe6
     * @return com.zy.website.ApiReturn
     * @author zhangyu
     * @date 2022/2/26 10:36
     */
    @RequestMapping(value = "getFilm", method = RequestMethod.GET)
    public ApiReturn getFilmByUid(@RequestParam String uid) throws Exception {
        ApiReturn apiReturn = new ApiReturn();
        FilmModel filmModel = filmService.getFilmByUid(uid);
        logger.info("获取视频信息:filModel={}", filmModel);
        if (filmModel != null) {
            apiReturn.setData(filmModel);
            apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        } else {
            apiReturn.setApiReturnCode(ApiReturnCode.NO_FILM_INFO);
        }
        return apiReturn;
    }

    /**
     * 视频榜
     *
     * @param typeCode 视频排榜唯一标识|FILM_ALL_NOTICE,FILM_NOTICE
     * @return com.zy.website.response.NoticeResponse
     * @author zhangyu
     * @description 根据标识获取视频榜数据
     * @date 2022/2/28 14:51
     */
    @RequestMapping(value = "videoChart", method = RequestMethod.GET)
    public NoticeResponse videoChart(@RequestParam String typeCode) {
        NoticeResponse noticeResponse = new NoticeResponse();

        List<NoticeDTO> noticeDTOS = filmService.videoChart(typeCode);
        if (noticeDTOS != null && noticeDTOS.size() > 0) {
            logger.info("获取视频榜:noticeDTOS={}", JSONObject.toJSONString(noticeDTOS));
            noticeResponse.setResultCode(ApiReturnCode.SUCCESSFUL.getCode());
            noticeResponse.setResultMsg(ApiReturnCode.SUCCESSFUL.getMessage());
            noticeResponse.setNoticeList(noticeDTOS);
        }
        return noticeResponse;
    }

    /**
     * 搜索框搜索
     *
     * @param filmSearchBarRequest
     * @return com.zy.website.ApiReturn
     * @author zhangyu
     * @description 首页搜索框
     * @date 2022/2/28 15:18
     */
    @RequestMapping(value = "filmSearchBar", method = RequestMethod.POST)
    public ApiReturn filmSearchBar(@RequestBody FilmSearchBarRequest filmSearchBarRequest) {
        ApiReturn apiReturn = filmService.filmSearchBar(filmSearchBarRequest);
        return apiReturn;
    }

    /**
     * 首页视频展示
     *
     * @return com.zy.website.response.TopFilmResponse
     * @author zhangyu
     * @description 首页根据顶部菜单视频展示
     * @date 2022/3/2 11:33
     */
    @RequestMapping(value = "frontPageFilm", method = RequestMethod.GET)
    public TopFilmResponse frontPageFilm() {
        TopFilmResponse topFilmResponse = filmService.frontPageFilm();
        return topFilmResponse;
    }


}

