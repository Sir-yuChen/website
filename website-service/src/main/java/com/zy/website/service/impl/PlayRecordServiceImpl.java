package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.ApiReturn;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.mapper.PlayRecordMapper;
import com.zy.website.model.PlayRecordModel;
import com.zy.website.model.dto.PlayRecordDTO;
import com.zy.website.request.PlayClearRecordRequest;
import com.zy.website.response.PlayRecordResponse;
import com.zy.website.service.PlayRecordService;
import com.zy.website.utils.RedisUtil;
import com.zy.website.variable.Variable;
import ma.glasnost.orika.MapperFacade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

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
    public PlayRecordResponse getPlayRecord(String key) {
        // 1 . 登录的用户这先到缓存中取，取不到这到数据库中取最新20条记录
        //2. 未登录用户 到redis中取缓存信息
        boolean b = redisUtil.hHasKey(Variable.REDIS_PLAYRECORD_KEY, key);
        PlayRecordResponse response = new PlayRecordResponse();
        if (!b) {
            logger.info("redis中缓存播放记录为空");
            response.setResultCode(ApiReturnCode.NO_PLAY_RECORD_DATA.getCode());
            response.setResultMsg(ApiReturnCode.NO_PLAY_RECORD_DATA.getMessage());
            return response;
        }
        List<PlayRecordDTO> recordDTOS = (List<PlayRecordDTO>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY, key);
        logger.info("redis中缓存播放记录：recordDTOS={}", JSONObject.toJSONString(recordDTOS));
        response.setPlayRecordList(recordDTOS);
        response.setResultCode(ApiReturnCode.SUCCESSFUL.getCode());
        response.setResultMsg(ApiReturnCode.SUCCESSFUL.getMessage());
        return response;
    }

    @Override
    public void setPlayRecord(PlayRecordDTO dto) {
        //非会员不落库
        //用户模块暂未开发，这里都落加缓存
        try {
            //TODO-zy 暂时写死
            dto.setPlayAccount("zhangyu123");
            List<PlayRecordDTO> recordDTOS = new ArrayList<>();
            if (redisUtil.hHasKey(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayIp())) {
                //缓存合并
                List<PlayRecordDTO> playRecordDTOS = (List<PlayRecordDTO>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayAccount() == null ? dto.getPlayIp() : dto.getPlayAccount());
                List<PlayRecordDTO> playRecordIpDTOS = (List<PlayRecordDTO>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY,dto.getPlayIp());
                if (playRecordDTOS != null) {
                    recordDTOS.addAll(playRecordDTOS);
                }
                if (playRecordIpDTOS != null) {
                    recordDTOS.addAll(playRecordIpDTOS);
                }
            }
            AtomicBoolean falg = new AtomicBoolean(false);
            recordDTOS.forEach(item -> {
                if (item.getPlayFilmUid().equals(dto.getPlayFilmUid())) {
                    falg.set(true);
                }
            });
            if (!falg.get()) {
                recordDTOS.add(dto);
                List<PlayRecordDTO> collect = recordDTOS.stream().distinct().collect(Collectors.toList());
                redisUtil.hset(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayAccount() == null ? dto.getPlayIp() : dto.getPlayAccount(), collect, 12 * 60 * 60);
            }
            if (Optional.ofNullable(dto.getPlayAccount()).isPresent()) {
                PlayRecordModel playRecordModel = playRecordMapper.selectOne(
                        new QueryWrapper<PlayRecordModel>().lambda()
                                .eq(PlayRecordModel::getPlayFilmUid, dto.getPlayFilmUid())
                                .eq(PlayRecordModel::getPlayAccount,dto.getPlayAccount())
                );
                if (playRecordModel == null) {
                    PlayRecordModel model = mapperFacade.map(dto, PlayRecordModel.class);
                    model.setCreactTime(new Date());
                    model.setPlayTime(dto.getPlayTime());
                    playRecordMapper.insert(model);
                }else {
                    playRecordModel.setPlayTime(new Date());
                    playRecordModel.setFilmUrl(dto.getFilmUrl());
                    playRecordModel.setFilmName(dto.getFilmName());
                    playRecordModel.setPlayIp(dto.getPlayIp());
                    playRecordModel.setPlayDuration(dto.getPlayDuration());
                    playRecordMapper.updateById(playRecordModel);
                }
            }
        } catch (Exception e) {
            logger.error("播放记录存储异常 PlayRecordDTO={}", JSONObject.toJSONString(dto));
        }
    }

    @Override
    public ApiReturn clearPlayRecord(PlayClearRecordRequest playClearRecordRequest, String ipAddr) {
        ApiReturn apiReturn = new ApiReturn();
        if (Optional.ofNullable(playClearRecordRequest.getUserUid()).isPresent()) {
            //登录用户 修改库中状态
            String[] split = playClearRecordRequest.getPlayRecordIds().trim().split(",");
            Arrays.stream(split).forEach(item -> {
                PlayRecordModel playRecordModel = new PlayRecordModel();
                playRecordModel.setRecordStatus(0);
                playRecordModel.setId(Integer.valueOf(item));
                playRecordMapper.updateById(playRecordModel);
            });
            redisUtil.hdel(Variable.REDIS_PLAYRECORD_KEY, playClearRecordRequest.getUserUid());
        }else {
            redisUtil.hdel(Variable.REDIS_PLAYRECORD_KEY, ipAddr);
        }
        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        return apiReturn;
    }
}
