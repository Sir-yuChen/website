package com.zy.website.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zy.website.ApiReturn;
import com.zy.website.code.ApiReturnCode;
import com.zy.website.enums.*;
import com.zy.website.exception.WebsiteBusinessException;
import com.zy.website.mapper.*;
import com.zy.website.model.*;
import com.zy.website.model.dto.FilmInfoExternalDTO;
import com.zy.website.model.dto.FilmInfoExternalDataDTO;
import com.zy.website.model.dto.MenuDTO;
import com.zy.website.model.dto.NoticeDTO;
import com.zy.website.request.FilmSearchBarRequest;
import com.zy.website.response.FilmSearchBarResponse;
import com.zy.website.response.TopFilmResponse;
import com.zy.website.service.FilmService;
import com.zy.website.service.thread.LargeDataThread;
import com.zy.website.utils.DateUtil;
import com.zy.website.utils.RedisUtil;
import com.zy.website.utils.RestTemplateUtils;
import com.zy.website.utils.UUIDGenerator;
import com.zy.website.utils.multi.FileUtils;
import com.zy.website.variable.MqConstant;
import com.zy.website.variable.Variable;
import ma.glasnost.orika.MapperFacade;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 服务实现类
 *
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
    FilmMenuMapper filmMenuMapper;
    @Resource
    FilmImageMapper filmImageMapper;
    @Resource
    ExternalMapper externalMapper;
    @Resource
    FilmTypeMapper filmTypeMapper;
    @Resource
    TypeRelationFilmMapper typeRelationFilmMapper;
    @Resource
    FilmScoreMapper filmScoreMapper;
    @Resource
    MapperFacade mapperFacade;
    @Resource
    RedisUtil redisUtil;
    @Resource
    FileService fileService;
    @Resource
    RestTemplateUtils restTemplateUtils;
    @Resource
    private MsgProductionService msgProductionService;
    @Resource
    private ThreadPoolTaskExecutor testTaskExecutor;//线程池;

    private int pageSize = 100;
    private int threadNum = 5;
    private static int queueCapacity = 1000;

    private static BlockingQueue<FilmModel> queue = new ArrayBlockingQueue<>(queueCapacity);


    private String DOWN_PATH = "D:\\idea-develop-project\\Project_All\\projectSevice\\website\\website-web\\src\\main\\resources\\static\\text";//文件下载地址
    private String DOWN_GITEE_PATH = "https://gitee.com/Sir-yuChen/backstage_ant_upload/raw/master/file/down_file_film/film.xml";//文件下载地址

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
                return;
            }
            switch (byCode) {
                case LEADERBOARD_ALL_NOTICE:   //所有榜单
                    //  所有榜单数据  使用异步执行多个方法
                    try {
                        noticeDTOS.clear();//查询所以保证返回的集合为null，避免数据重复
                        CompletableFuture<NoticeDTO> filmDto = CompletableFuture.supplyAsync(() -> getFilmNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_FILM_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> varietyDto = CompletableFuture.supplyAsync(() -> getVarietyNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_VARIETY_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> cartoonDto = CompletableFuture.supplyAsync(() -> getCartoonNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_CARTOON_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> usdramaDto = CompletableFuture.supplyAsync(() -> getUsdramaNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_USDRAMA_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> hitDto = CompletableFuture.supplyAsync(() -> getHitNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_HIT_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> scoreDto = CompletableFuture.supplyAsync(() -> getScoreNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_SCORE_NOTICE.getCode()));
                        CompletableFuture<NoticeDTO> episodeDto = CompletableFuture.supplyAsync(() -> getEpisodeNoticeData(Variable.REDIS_NOTICES_KEY, FilmLeaderboardEnum.LEADERBOARD_EPISODE_NOTICE.getCode()));
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
                    // todo 如果FILM_ALL_NOTICE 标识，这不在执行其它标识下代码打断所以循环

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

    @Override
    public TopFilmResponse frontPageFilm() {
        TopFilmResponse topFilmResponse = new TopFilmResponse();
        topFilmResponse.setResultCode(ApiReturnCode.SUCCESSFUL.getCode());
        topFilmResponse.setResultMsg(ApiReturnCode.SUCCESSFUL.getMessage());
        //查配置的首页顶部的菜单
        QueryWrapper<FilmMenuModel> query = new QueryWrapper<>();
        query.lambda()
                .eq(FilmMenuModel::getMenuType, MenuTypeEnum.MENU_TOP.getCode())
                .eq(FilmMenuModel::getMenuStatus, WebsiteStatusEnum.STATUS_MENU_Y.getCode());
        List<FilmMenuModel> filmMenuModels = filmMenuMapper.selectList(query);
        if (filmMenuModels == null || filmMenuModels.size() == 0) {
            logger.error("首页菜单类型 视频展示列表 ，查询菜单集合为空,配置异常 filmMenuModels={}",
                    JSONObject.toJSONString(filmMenuModels));
            topFilmResponse.setResultCode(ApiReturnCode.HTTP_ERROR.getCode());
            topFilmResponse.setResultMsg(ApiReturnCode.HTTP_ERROR.getMessage());
            throw new WebsiteBusinessException("菜单集合为空,配置异常", ApiReturnCode.HTTP_ERROR.getCode());
        }
        List<MenuDTO> menuDTOs = new ArrayList<>();
        filmMenuModels.stream().filter(item -> Optional.ofNullable(item.getMenuMark()).isPresent()).forEach(menu -> {
            //视频筛选条件 时间排序 播放量大于 1000 在以播放量排序
            List<FilmModel> filmModel = filmMapper.selectFrontList(menu.getMenuMark());
            //拼装参数
            MenuDTO menuDTO = mapperFacade.map(menu, MenuDTO.class);
            menuDTO.setChildList(filmModel);
            if (filmModel != null) {
                menuDTOs.add(menuDTO);
            }
        });
        menuDTOs.stream().sorted(Comparator.comparing(MenuDTO::getMenuSequence));
        logger.info("首页菜单类型 视频展示列表 数据集合menuDTOs={}", JSONObject.toJSONString(menuDTOs));
        topFilmResponse.setTopFimlList(menuDTOs);
        return topFilmResponse;
    }

    @Override
    public ApiReturn refreshFilmData() {
        ApiReturn apiReturn = new ApiReturn();
        //加载外部视频信息
        this.refreshFilmInfo();
        apiReturn.setCode(ApiReturnCode.SUCCESSFUL.getCode());
        apiReturn.setMsg(ApiReturnCode.SUCCESSFUL.getMessage());
        return apiReturn;
    }

    @Override
    public ApiReturn refreshTop250FilmData() {
        ApiReturn apiReturn = new ApiReturn();
        //top250
        this.getFilmTop();
        apiReturn.setCode(ApiReturnCode.SUCCESSFUL.getCode());
        apiReturn.setMsg(ApiReturnCode.SUCCESSFUL.getMessage());
        return apiReturn;
    }

    //读取文件搜索关键词放MQ中
    public void refreshFilmInfo() {
        //根据标识查接口信息
        try {
            //定时任务刷新 视频数据  包括新视频入库  视频地址刷新
            ExternalModel externalModel = this.selectExternalModel(ExternalEnum.WMDB_TV.getCode());
            //将电影名称放到文件服务器中 ，下载文件批量请求加载数据  这里使用gitee作为文件服务器
            //1.下载文件到本地  下载地址配置
            FileUtils.downloadToServer(DOWN_GITEE_PATH, DOWN_PATH, externalModel.getApiName() + ".text");
            //2. 多线程读取本地文件
            File file = new File(DOWN_PATH + "//" + externalModel.getApiName() + ".text");
            FileInputStream input = new FileInputStream(file);
            //File---> MultipartFile
            MultipartFile multipartFile = new MockMultipartFile(externalModel.getApiName() + ".txt", file.getName(), "text/plain", IOUtils.toByteArray(input));
            List<String> filmNameList = fileService.uploadByThread(multipartFile);
            //将读取的数据放入MQ延时队列中
            AtomicInteger time = new AtomicInteger(40);
            filmNameList.forEach(filmName -> {
                time.addAndGet(40);
                //交换机 ，MQ内容，路由键这里没有实质作用 延迟时间单位S
                msgProductionService.sendTimeoutMsg(UUIDGenerator.getUUIDReplace(), MqConstant.MQ_WEBSITE_FILM_DELAY_EXCHANGE, filmName, UUIDGenerator.getUUIDReplace(), time.get());
            });
            //删除本地文件  确认MQ中的所有延时消息已经处理完成 则删除文件
            FileUtils.deleteFile(DOWN_PATH + "//" + externalModel.getApiName() + ".text");
        } catch (Exception e) {
            logger.error("视频模糊查询 文件读取/发送MQ 异常", e);
            throw new WebsiteBusinessException("视频模糊查询 文件读取/发送MQ 异常", ApiReturnCode.HTTP_ERROR.getCode());
        }
    }

    //T250
    public void getFilmTop() {
        //WMDB_TV_250
        ExternalModel externalModel = this.selectExternalModel(ExternalEnum.WMDB_TV_250.getCode());
        String specificParams = externalModel.getSpecificParams();
        String[] paramsName = specificParams.split(",");
        Map<String, Object> params = new HashMap<>();
        Arrays.stream(paramsName).forEach(n -> {
            // 从配置中心取 与数据库中比较 来传递本次需要添加的参数
            if (n.equals("type")) {
                params.put(n, "Imdb");
            }
            if (n.equals("skip")) {
                params.put(n, 0);
            }
            if (n.equals("limit")) {
                params.put(n, 250);
            }
            if (n.equals("lang")) {
                params.put(n, "Cn");
            }
            //其他参数
        });
        try {
            List list = restTemplateUtils.httpGetTraditional(externalModel.getSpecificUrl(), params, null, List.class);
            List<FilmInfoExternalDTO> listExternalDTO = mapperFacade.mapAsList(list, FilmInfoExternalDTO.class);
            List<FilmInfoExternalDTO> filmInfoExternalDTOS = new ArrayList<>();
            listExternalDTO.forEach(item -> {
                List<FilmInfoExternalDataDTO> filmInfoExternalDataDTOS = mapperFacade.mapAsList(item.getData(), FilmInfoExternalDataDTO.class);
                item.setData(filmInfoExternalDataDTOS);
                filmInfoExternalDTOS.add(item);
            });
            filmInfoExternalDTOS.forEach(externalDTO -> {
                if (externalDTO == null || externalDTO.getData() == null || externalDTO.getData().size() == 0) {
                    logger.error("视频T250第三方接口 未获取到数据,URL={},params={}",
                            externalModel.getSpecificUrl(), JSONObject.toJSONString(params));
                    return;
                }
                boolean b = this.saveFileInfo(externalDTO);
                if (!b) {
                    return;
                }
            });
        } catch (Exception e) {
            logger.error("视频T250第三方接口 接口调用异常,URL={},params={}",
                    externalModel.getSpecificUrl(), JSONObject.toJSONString(params));
            return;
        }
    }

    public ExternalModel selectExternalModel(String mark) {
        ExternalModel externalModel = externalMapper.selectOne(new QueryWrapper<ExternalModel>().lambda()
                .eq(ExternalModel::getStatus, "Y").eq(ExternalModel::getPlatformMark, mark));
        logger.info("第三方API 配置 externalModel={}", JSONObject.toJSONString(externalModel));
        if (externalModel == null) {
            logger.error("第三方API 配置异常 请检查配置");
            throw new WebsiteBusinessException("第三方API 配置异常 请检查配置", ApiReturnCode.HTTP_ERROR.getCode());
        }
        //放缓存中 有效期一小时
        redisUtil.set(Variable.REDIS_EXTERNAL_INFO_KEY + "_" + mark, externalModel, 60 * 60);
        return externalModel;
    }

    //调用第三方API 接口 获取视频详情落库 搜索条件 视频名 人名
    @Override
    public void getFilmInfoByExternalApi(String filmKeyWord) {
        ExternalModel externalModel = null;
        String redisKey = Variable.REDIS_EXTERNAL_INFO_KEY + "_" + ExternalEnum.WMDB_TV.getCode();
        boolean b1 = redisUtil.hasKey(redisKey);
        if (b1) {
            externalModel = (ExternalModel) redisUtil.get(redisKey);
        } else {
            externalModel = externalMapper.selectOne(new QueryWrapper<ExternalModel>().lambda()
                    .eq(ExternalModel::getStatus, "Y").eq(ExternalModel::getPlatformMark, ExternalEnum.WMDB_TV.getCode()));
        }
        String specificParams = externalModel.getSpecificParams();
        String[] paramsName = specificParams.split(",");
        // 视频数据加载 请求第三方API
        Map<String, Object> params = new HashMap<>();
        Arrays.stream(paramsName).forEach(n -> {
            // 从配置中心取 与数据库中比较 来传递本次需要添加的参数
            if (n.equals("q")) {
                params.put(n, filmKeyWord);
            }
            if (n.equals("lang")) {
                params.put(n, "Cn");
            }
            //其他参数
        });

        List<FilmInfoExternalDTO> filmInfoExternalDTOList = null;
        try {
            if (externalModel.getRequestType().equals("POST")) {
                filmInfoExternalDTOList = restTemplateUtils.httpPostJson(externalModel.getSpecificUrl(), params, null, List.class);
            } else if (externalModel.getRequestType().equals("GET")) {
                List list = restTemplateUtils.httpGetTraditional(externalModel.getSpecificUrl(), params, null, List.class);
                filmInfoExternalDTOList = mapperFacade.mapAsList(list, FilmInfoExternalDTO.class);
            }
            if (filmInfoExternalDTOList == null || filmInfoExternalDTOList.size() == 0) {
                logger.error("视频模糊查询第三方接口 未获取到数据,URL={},params={}",
                        externalModel.getSpecificUrl(), JSONObject.toJSONString(params));
                return;
            }
            logger.info("视频模糊查询第三方API filmInfoExternalDTOList={}", JSONObject.toJSONString(filmInfoExternalDTOList));
        } catch (Exception e) {
            logger.error("视频模糊查询第三方接口 接口调用异常,URL={},params={}",
                    externalModel.getSpecificUrl(), JSONObject.toJSONString(params));
            return;
        }
        filmInfoExternalDTOList.forEach(externalDto -> {
            List<FilmInfoExternalDataDTO> dataDTOS = mapperFacade.mapAsList(externalDto.getData(), FilmInfoExternalDataDTO.class);
            externalDto.setData(dataDTOS);
            boolean b = this.saveFileInfo(externalDto);
            if (!b) {
                return;
            }
        });
    }


    @Override
    public void largeDataJob() {

        try {
            Page<FilmModel> filmPage = new Page<>(0, pageSize);   //查询第pageNum页，每页pageSize条数据
            Page<FilmModel> filmModelPage = filmMapper.selectPage(filmPage, new QueryWrapper<>());
            List<FilmModel> records = filmModelPage.getRecords();
            if (records.size() == 0 || records.isEmpty()) {
                logger.info("未查询到数据 JOB 处理完成");
                return;
            }

            long totalPages = (filmModelPage.getTotal() + filmPage.getSize() - 1) / filmPage.getSize();

            for (FilmModel filmModel : records) {
                queue.offer(filmModel, 5, TimeUnit.SECONDS);
            }
            List<Future<Integer>> failList = new ArrayList<>();
            for (int i = 0; i < threadNum; i++) {
                Future<Integer> failCount = testTaskExecutor
                        .submit(new LargeDataThread(queue));
                failList.add(failCount);
            }

            if (totalPages > 1) {
                for (int i = 1; i <= totalPages; i++) {
                    records = filmMapper.selectPage(new Page<>(i, pageSize), new QueryWrapper<>()).getRecords();
                    for (FilmModel filmModel : records) {
                        queue.offer(filmModel, 5, TimeUnit.SECONDS);
                    }
                }
            }
            int count;
            count = failList.stream().mapToInt(this::getCount).sum();
            logger.warn("线程池 统计失败数量：{}", count);
        } catch (InterruptedException e) {
            logger.error("线程池 job 发生中断异常：", e);
        }
    }

    private int getCount(Future<Integer> fail) {
        int n = 0;
        try {
            n = fail.get();
        } catch (InterruptedException e) {
            logger.error("线程池 job 发生中断异常：", e);
        } catch (ExecutionException e) {
            logger.error("线程池 线程返回结果异常：", e);
        }
        return n;
    }


    //事务
    @Transactional(rollbackFor = Exception.class)
    public boolean saveFileInfo(FilmInfoExternalDTO filmInfoExternalDTO) {
        AtomicBoolean falg = new AtomicBoolean(false);
        //3. 数据整理落库
        try {
            List<FilmInfoExternalDataDTO> data = filmInfoExternalDTO.getData();
            FilmInfoExternalDTO finalFilmInfoExternalDTO = filmInfoExternalDTO;
            data.forEach(dto -> {
                //数据库中是否已经存在当前视频信息
                Integer integer = filmMapper.selectCount(new QueryWrapper<FilmModel>().lambda().eq(FilmModel::getFilmUid, dto.getMovie()));
                if (integer > 0) {
                    logger.error("当前视频信息已经存在,filmInfo={}", JSONObject.toJSONString(dto));
                    return;
                }
                //封面
                String uuidReplace = UUIDGenerator.getUUIDReplace();
                String poster = dto.getPoster().substring(dto.getPoster().lastIndexOf("/") + 1, dto.getPoster().lastIndexOf(".") + 1);
                FilmImageModel imageModel = new FilmImageModel().setCreactTime(new Date()).setImgName(uuidReplace).setImgRemark(dto.getName()).setImgUid(uuidReplace)
                        .setImgOriginal(poster).setImgType(FilmImageEnum.POSTER.getCode()).setImgSuffix(dto.getPoster().substring(dto.getPoster().lastIndexOf(".") + 1));
                filmImageMapper.insert(imageModel);
                //视频类型
                String genre = dto.getGenre();
                String[] split = genre.trim().split("/");
                for (int i = 0; i < split.length; i++) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("type_name", split[i]);

                    //类型不存在新增
                    List<FilmTypeModel> filmTypeModels = filmTypeMapper.selectByMap(map);
                    //类型视频新增
                    TypeRelationFilmModel typeRelationFilmModel = new TypeRelationFilmModel();
                    typeRelationFilmModel.setFilmUid(dto.getMovie());
                    FilmTypeModel filmTypeModel = new FilmTypeModel();
                    if (filmTypeModels == null || filmTypeModels.size() == 0) {
                        filmTypeModel.setTypeAssort("TYPE");
                        filmTypeModel.setTypeName(split[i]);
                        filmTypeModel.setTypeRevealName(split[i]);
                        filmTypeModel.setTypeMark(getRandomString(8));
                        filmTypeMapper.insert(filmTypeModel);
                        typeRelationFilmModel.setTypeId(filmTypeModel.getId());
                        typeRelationFilmMapper.insert(typeRelationFilmModel);
                    } else {
                        filmTypeModels.forEach(ty -> {
                            typeRelationFilmModel.setTypeId(ty.getId());
                            typeRelationFilmMapper.insert(typeRelationFilmModel);
                        });
                    }
                }
                //视频评分信息
                if (Optional.ofNullable(finalFilmInfoExternalDTO.getDoubanRating()).isPresent()) {
                    //豆瓣
                    FilmScoreModel scoreModel = new FilmScoreModel().setCreactTime(new Date()).setFilmUid(dto.getMovie())
                            .setScorePlatform(dto.getName() + "豆瓣评分").setScoreRatio(new BigDecimal(finalFilmInfoExternalDTO.getDoubanRating()))
                            .setSequence(1);
                    filmScoreMapper.insert(scoreModel);
                }

                //视频信息新增
                FilmModel filmModel = new FilmModel().setCreactTime(new Date())
                        .setFilmAlias(finalFilmInfoExternalDTO.getAlias())
                        .setFilmDescription(dto.getDescription())
                        .setFilmUid(dto.getMovie())
                        .setFilmName(dto.getName())
                        .setFilmOriginalName(finalFilmInfoExternalDTO.getOriginalName())
                        .setFilmLanguage(dto.getLanguage())
                        .setFilmLang(dto.getLang())
                        .setFilmPublishCountry(dto.getCountry())
                        .setFilmPublishTime(DateUtil.parseDate(finalFilmInfoExternalDTO.getDateReleased(), DateUtil.YYYY_MM_DD))
                        .setFilmPoster(String.valueOf(imageModel.getId()))
                        .setFilmShareimage(dto.getShareImage())
                        .setFilmPlayCount(Long.valueOf(new Random().nextInt(100000) % (100000 - 1000 + 1) + 1000));
                //处理类型
                if (finalFilmInfoExternalDTO.getType().equals("Movie")) {
                    filmModel.setFilmGenre(FilmGenreEnum.GENRE_FILM.getCode());
                } else if (finalFilmInfoExternalDTO.getType().equals("TVSeries")) {
                    filmModel.setFilmGenre(FilmGenreEnum.GENRE_EPISODE.getCode());
                }
                filmMapper.insert(filmModel);
            });
            falg.set(true);
        } catch (Exception e) {
            logger.error("新增视频信息 异常 filmInfoExternalDTO={} e={}",
                    JSONObject.toJSONString(filmInfoExternalDTO), e);
        }
        return falg.get();
    }

    //length用户要求产生字符串的长度
    public static String getRandomString(int length) {
        String str = "QWERTYUIOPASDFGHJKLZXCVBNM_";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(27);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    //1. 电影排行榜  FILM_NOTICE 榜单数据处理方法
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
