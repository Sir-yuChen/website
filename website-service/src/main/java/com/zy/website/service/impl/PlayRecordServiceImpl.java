package com.zy.website.service.impl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.ApiReturn;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.enums.PlayRecordEnum;
import com.zy.website.exception.WebsiteBusinessException;
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
import org.springframework.util.CollectionUtils;

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
        if (!b && isIPAddressByRegex(key)) {
            logger.info("redis中缓存播放记录为空");
            response.setResultCode(ApiReturnCode.NO_PLAY_RECORD_DATA.getCode());
            response.setResultMsg(ApiReturnCode.NO_PLAY_RECORD_DATA.getMessage());
            return response;
        }
        List<PlayRecordModel> recordDTOS = (List<PlayRecordModel>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY, key);
        logger.info("redis中缓存播放记录：recordDTOS={}", JSONObject.toJSONString(recordDTOS));
        //缓存没有查数据库
        if (CollectionUtils.isEmpty(recordDTOS) && !isIPAddressByRegex(key)) {
            List<PlayRecordModel> playRecordModels = playRecordMapper.selectList(
                    new QueryWrapper<PlayRecordModel>().lambda()
                            .eq(PlayRecordModel::getRecordStatus, PlayRecordEnum.PLAY_RECORD_NORMAL.getCode())
                            .eq(PlayRecordModel::getPlayAccount, key)
                            .orderByDesc(PlayRecordModel::getCreactTime)
                            .last("limit 10")
            );
            if (playRecordModels.size() > 0) {
                recordDTOS = new ArrayList<>();
                redisUtil.hset(Variable.REDIS_PLAYRECORD_KEY, key, playRecordModels, 12 * 60 * 60);
                recordDTOS.addAll(playRecordModels);
            }
        }
        if (CollectionUtils.isEmpty(recordDTOS)) {
            logger.info("用户不存在播放记录");
            response.setResultCode(ApiReturnCode.NO_PLAY_RECORD_DATA.getCode());
            response.setResultMsg(ApiReturnCode.NO_PLAY_RECORD_DATA.getMessage());
            return response;
        }
        recordDTOS.forEach(x -> {
            if (x.getId() == null) {
                PlayRecordModel playRecordModel = playRecordMapper.selectOne(
                        new QueryWrapper<PlayRecordModel>().lambda()
                                .eq(PlayRecordModel::getPlayFilmUid, x.getPlayFilmUid())
                                .eq(PlayRecordModel::getPlayAccount, x.getPlayAccount()));
                x.setId(playRecordModel.getId());
            }
        });
        response.setPlayRecordList(recordDTOS.stream().sorted(Comparator.comparing(PlayRecordModel::getId).reversed()).collect(Collectors.toList()));
        response.setResultCode(ApiReturnCode.SUCCESSFUL.getCode());
        response.setResultMsg(ApiReturnCode.SUCCESSFUL.getMessage());
        return response;
    }

    @Override
    public ApiReturn setPlayRecord(PlayRecordDTO dto) {
        ApiReturn apiReturn = new ApiReturn();
        //非会员不落库
        //用户模块暂未开发，这里都落加缓存
        try {
            //TODO-zy 暂时写死
            dto.setPlayAccount("zhangyu123");
            List<PlayRecordModel> recordDTOS = new ArrayList<>();
            boolean b = false;
            if (Optional.ofNullable(dto.getPlayAccount()).isPresent()) {
                b = redisUtil.hHasKey(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayAccount());
            } else {
                b = redisUtil.hHasKey(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayIp());
            }
            if (b) {
                //缓存合并
                List<PlayRecordModel> playRecordDTOS = (List<PlayRecordModel>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayAccount() == null ? dto.getPlayIp() : dto.getPlayAccount());
                List<PlayRecordModel> playRecordIpDTOS = (List<PlayRecordModel>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayIp());
                if (playRecordDTOS != null) {
                    recordDTOS.addAll(playRecordDTOS);
                }
                if (playRecordIpDTOS != null) {
                    recordDTOS.addAll(playRecordIpDTOS);
                }
            }
            List<PlayRecordModel> records = new ArrayList<>();
            if (CollectionUtils.isEmpty(recordDTOS)) {
                PlayRecordModel dtomodel = mapperFacade.map(dto, PlayRecordModel.class);
                dtomodel.setCreactTime(new DateTime());
                records.add(dtomodel);
            }
            AtomicBoolean flgSame = new AtomicBoolean(false);
            recordDTOS.forEach(item -> {
                if (item.getPlayFilmUid().equals(dto.getPlayFilmUid())) {
                    PlayRecordModel model = mapperFacade.map(dto, PlayRecordModel.class);
                    model.setCreactTime(new DateTime());
                    records.add(model);
                    flgSame.set(true);
                } else {
                    PlayRecordModel playRecordModel = playRecordMapper.selectOne(
                            new QueryWrapper<PlayRecordModel>().lambda()
                                    .eq(PlayRecordModel::getPlayFilmUid, item.getPlayFilmUid())
                                    .eq(PlayRecordModel::getPlayAccount, item.getPlayAccount()));
                    if (playRecordModel != null) {
                        records.add(playRecordModel);
                    }
                }
            });
            if (!flgSame.get()) {
                PlayRecordModel model = mapperFacade.map(dto, PlayRecordModel.class);
                model.setCreactTime(new DateTime());
                records.add(model);
            }
            if (!CollectionUtils.isEmpty(records)) {
                //更加集合对象的某一个字段去重
                List<PlayRecordModel> collect = records.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(PlayRecordModel::getPlayFilmUid))), ArrayList::new));
                List<PlayRecordModel> recordModels = collect.stream().sorted(Comparator.comparing(PlayRecordModel::getCreactTime).reversed()).limit(10).collect(Collectors.toList());
                redisUtil.hset(Variable.REDIS_PLAYRECORD_KEY, dto.getPlayAccount() == null ? dto.getPlayIp() : dto.getPlayAccount(), recordModels, 12 * 60 * 60);
            }
            if (Optional.ofNullable(dto.getPlayAccount()).isPresent()) {
                PlayRecordModel playRecordModel = playRecordMapper.selectOne(
                        new QueryWrapper<PlayRecordModel>().lambda()
                                .eq(PlayRecordModel::getPlayFilmUid, dto.getPlayFilmUid())
                                .eq(PlayRecordModel::getPlayAccount, dto.getPlayAccount())
                );
                if (playRecordModel == null) {
                    PlayRecordModel model = mapperFacade.map(dto, PlayRecordModel.class);
                    model.setPlayTime(dto.getPlayTime());
                    playRecordMapper.insert(model);
                } else {
                    playRecordModel.setPlayTime(new DateTime());
                    playRecordModel.setCreactTime(new DateTime());
                    playRecordModel.setFilmUrl(dto.getFilmUrl());
                    playRecordModel.setFilmName(dto.getFilmName());
                    playRecordModel.setPlayIp(dto.getPlayIp());
                    playRecordModel.setPlayDuration(dto.getPlayDuration());
                    playRecordMapper.updateById(playRecordModel);
                }
            }
        } catch (Exception e) {
            logger.error("播放记录存储异常 PlayRecordDTO={}", JSONObject.toJSONString(dto));
            apiReturn.setApiReturnCode(ApiReturnCode.HTTP_ERROR);
            throw new WebsiteBusinessException("播放记录存储异常", ApiReturnCode.HTTP_ERROR.getCode());
        }
        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        return apiReturn;
    }

    @Override
    public ApiReturn clearPlayRecord(PlayClearRecordRequest playClearRecordRequest, String ipAddr) {
        ApiReturn apiReturn = new ApiReturn();
        String[] split = playClearRecordRequest.getPlayRecordIds().trim().split(",");
        if (Optional.ofNullable(playClearRecordRequest.getUserUid()).isPresent()) {
            //登录用户 修改库中状态
            Arrays.stream(split).forEach(item -> {
                PlayRecordModel playRecordModel = new PlayRecordModel();
                playRecordModel.setRecordStatus(Integer.valueOf(PlayRecordEnum.PLAY_RECORD_DEL.getCode()));
                playRecordModel.setId(Integer.valueOf(item));
                playRecordMapper.updateById(playRecordModel);
            });
        }
        List<PlayRecordModel> recordDTOS = (List<PlayRecordModel>) redisUtil.hget(Variable.REDIS_PLAYRECORD_KEY, playClearRecordRequest.getUserUid() == null ? ipAddr : playClearRecordRequest.getUserUid());
        List<PlayRecordModel> records = new ArrayList<>();
        if (!CollectionUtils.isEmpty(recordDTOS)) {
            recordDTOS.forEach(x -> {
                AtomicBoolean falg = new AtomicBoolean(false);
                Arrays.stream(split).forEach(s -> {
                    if (x.getId().toString().equals(s)) {
                        logger.info("删除缓存中播放记录");
                        falg.set(true);
                    }
                });
                if (!falg.get()) {
                    records.add(x);
                }
            });
        }
        List<PlayRecordModel> collect = records.stream().distinct().collect(Collectors.toList());
        redisUtil.hset(Variable.REDIS_PLAYRECORD_KEY, playClearRecordRequest.getUserUid() == null ? ipAddr : playClearRecordRequest.getUserUid(), collect, 12 * 60 * 60);
        apiReturn.setApiReturnCode(ApiReturnCode.SUCCESSFUL);
        return apiReturn;
    }

    /**
     * 用正则表达式进行判断
     */
    public boolean isIPAddressByRegex(String str) {
        String regex = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
        // 判断ip地址是否与正则表达式匹配
        if (str.matches(regex)) {
            String[] arr = str.split("\\.");
            for (int i = 0; i < 4; i++) {
                int temp = Integer.parseInt(arr[i]);
                //如果某个数字不是0到255之间的数 就返回false
                if (temp < 0 || temp > 255) return false;
            }
            return true;
        } else return false;
    }

}
