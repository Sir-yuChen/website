package com.zy.website.service.impl;

import com.zy.website.mapper.PersonInfoMapper;
import com.zy.website.service.PersonInfoService;
import com.zy.website.facade.model.PersonInfoModel;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhangyu
 * @since 2022-02-24
 */
@Service
public class PersonInfoServiceImpl extends ServiceImpl<PersonInfoMapper, PersonInfoModel> implements PersonInfoService {

}
