package com.zy.website.controller;


import com.zy.website.ApiReturn;
import com.zy.website.dto.PlayRecordDTO;
import com.zy.website.request.PlayRecordRequest;
import com.zy.website.response.PlayRecordResponse;
import com.zy.website.service.PlayRecordService;
import com.zy.website.utils.DateUtil;
import com.zy.website.utils.NetUtils;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 播放记录 PlayRecordController
 * @author zhangyu
 * @since 2022-02-26
 */
@RestController
@RequestMapping("/api/play/")
public class PlayRecordController extends BaseController {

    private static Logger logger = LogManager.getLogger(PlayRecordController.class);

    @Resource
    PlayRecordService playRecordService;

    @Resource
    MapperFacade mapperFacade;

    /**
     * 获取视频播放记录
     * @param request
     * @return com.zy.website.ApiReturn
     * @author zhangyu
     * @date 2022/3/1 16:42
     */
    @RequestMapping(value = "record", method = RequestMethod.GET)
    public PlayRecordResponse getPlayRecord(HttpServletRequest request) {
        String ipAddr = NetUtils.getIpAddr(request);
        PlayRecordResponse response = playRecordService.getPlayRecord(ipAddr);
        return response;
    }

   /**
    * 保存视频播放记录
    * @author zhangyu
    * @param playRecordRequest
    * @description  保存视频播放记录
    * @date 2022/3/1 18:19
    * @return void
    */
    @RequestMapping(value = "saveRecord", method = RequestMethod.POST)
    public void setPlayRecord(@RequestBody PlayRecordRequest playRecordRequest, HttpServletRequest request) {
        String ipAddr = NetUtils.getIpAddr(request);
        PlayRecordDTO dto = mapperFacade.map(playRecordRequest, PlayRecordDTO.class);
        dto.setPlayIp(ipAddr);
        dto.setPlayTime(DateUtil.format(new Date(), DateUtil.YYYY_MM_DD_HHMMSS));
        playRecordService.setPlayRecord(dto);
    }

    /**
     *  清除播放记录
     * @author zhangyu
     * @param playRecordIds 播放记录ID|1,2,3,4,5
     * @description清除播放记录
     * @date 2022/3/1 18:20
     * @return com.zy.website.ApiReturn
     */
    @RequestMapping(value = "clearRecord", method = RequestMethod.GET)
    public ApiReturn clearPlayRecord(@RequestParam String playRecordIds, HttpServletRequest request) {
        String ipAddr = NetUtils.getIpAddr(request);
        ApiReturn apiReturn = playRecordService.clearPlayRecord(playRecordIds, ipAddr);
        return apiReturn;
    }


    


}

