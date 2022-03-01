package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.ApiReturn;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.dto.PlayRecordDTO;
import com.zy.website.mapper.PlayRecordMapper;
import com.zy.website.model.PlayRecordModel;
import com.zy.website.response.PlayRecordResponse;
import com.zy.website.service.PlayRecordService;
import com.zy.website.utils.DateUtil;
import com.zy.website.utils.RedisUtil;
import com.zy.website.variable.Variable;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyu
 * @since 2022-02-26
 */
@Service("playRecordService")
public class PlayRecordServiceImpl extends ServiceImpl<PlayRecordMapper, PlayRecordModel> implements PlayRecordService {

    private static Logger logger = LogManager.getLogger(PlayRecordServiceImpl.class);

    @Resource
    RedisUtil redisUtil;

    @Resource
    PlayRecordMapper playRecordMapper;

    @Resource
    MapperFacade mapperFacade;

    @Override
    public PlayRecordResponse getPlayRecord(String ipAddr) {
        // 1 . 登录的用户这先到缓存中取，取不到这到数据库中取最新20条记录
        //2. 未登录用户 到redis中取缓存信息
        boolean b = redisUtil.hHasKey(Variable.REDIS_PLAYRECORD_KEY, ipAddr);
        PlayRecordResponse response = new PlayRecordResponse();
        if (!b) {
            logger.info("redis中缓存播放记录为空");
            response.setResultCode(ApiReturnCode.NO_PLAY_RECORD_DATA.getCode());
            response.setResultMsg(ApiReturnCode.NO_PLAY_RECORD_DATA.getMessage());
            return response;
        }
        List<PlayRecordDTO> recordDTOS = (List<PlayRecordDTO>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY, ipAddr);
        logger.info("redis中缓存播放记录：recordDTOS={}", JSONObject.toJSONString(recordDTOS));
        return response;
    }

    @Override
    public void setPlayRecord(PlayRecordDTO dto) {
        //非会员不落库
        //用户模块暂未开发，这里都落加缓存
       try {
           redisUtil.hset(Variable.REDIS_PLAYRECORD_KEY,dto.getPlayIp(),dto,12*60*60);
           PlayRecordModel model = mapperFacade.map(dto, PlayRecordModel.class);
           model.setCreactTime(new Date());
           model.setPlayTime(DateUtil.parseDate(dto.getPlayTime()));
           playRecordMapper.insert(model);
       }catch (Exception e){
           logger.error("播放记录存储异常 PlayRecordDTO={}",JSONObject.toJSONString(dto));
       }
    }

    @Override
    public ApiReturn clearPlayRecord(String playRecordIds, String ipAddr) {
        ApiReturn apiReturn = new ApiReturn();
        //登录用户 修改库中状态
        String[] split = playRecordIds.trim().split(",");
        Arrays.stream(split).forEach(item->{
            PlayRecordModel playRecordModel = new PlayRecordModel();
            playRecordModel.setRecordStatus(0);
            playRecordModel.setId(Integer.valueOf(item));
            playRecordMapper.updateById(playRecordModel);
        });
        redisUtil.hdel(Variable.REDIS_PLAYRECORD_KEY,ipAddr);
        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        return apiReturn;
    }
}
