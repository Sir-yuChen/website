-- MySQL dump 10.13  Distrib 5.7.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: website
-- ------------------------------------------------------
-- Server version	5.7.34

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `t_dic_data`
--

DROP TABLE IF EXISTS `t_dic_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_dic_data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dic_type_uid` varchar(200) DEFAULT NULL COMMENT '字典类型唯一uid',
  `dic_sort` int(4) DEFAULT NULL COMMENT '字典排序',
  `dic_label` varchar(100) DEFAULT NULL COMMENT '字典标签',
  `dic_value` varchar(100) DEFAULT NULL COMMENT '字典键值',
  `dic_default` char(1) DEFAULT NULL COMMENT '是否默认Y/N',
  `dic_isFixed` tinyint(2) DEFAULT '0' COMMENT '0默认为不固定,1固定',
  `dic_status` varchar(20) NOT NULL DEFAULT 'Y' COMMENT '状态 Y正常 N暂停 D删除',
  `parent_id` int(11) DEFAULT '0' COMMENT '父ID',
  `dic_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dic_data`
--

LOCK TABLES `t_dic_data` WRITE;
/*!40000 ALTER TABLE `t_dic_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_dic_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_dictionary`
--

DROP TABLE IF EXISTS `t_dictionary`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_dictionary` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dic_type_uid` varchar(200) DEFAULT NULL COMMENT '字典类型唯一uid',
  `dic_type_key` varchar(128) DEFAULT NULL COMMENT '字典类型KEY',
  `dic_type_value` varchar(200) DEFAULT NULL COMMENT '字典类型value',
  `dic_type_status` varchar(20) NOT NULL DEFAULT 'Y' COMMENT '状态 Y正常 N暂停 D删除',
  `dic_type_desc` varchar(256) DEFAULT NULL COMMENT '字典类型描述',
  `dic_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_dictionary`
--

LOCK TABLES `t_dictionary` WRITE;
/*!40000 ALTER TABLE `t_dictionary` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_dictionary` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_external`
--

DROP TABLE IF EXISTS `t_external`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_external` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `platform_name` varchar(150) DEFAULT NULL COMMENT '接口平台',
  `api_name` varchar(150) DEFAULT NULL COMMENT '接口名称',
  `platform_mark` varchar(100) DEFAULT NULL COMMENT '平台标识，自定义',
  `domain` varchar(100) DEFAULT NULL COMMENT '域名/IP',
  `specific_url` varchar(255) NOT NULL COMMENT '具体路径',
  `request_type` varchar(100) NOT NULL COMMENT '请求方式post/get',
  `specific_params` varchar(255) DEFAULT NULL COMMENT '入参名 逗号分隔',
  `status` varchar(20) DEFAULT 'Y' COMMENT 'Y/N',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_external`
--

LOCK TABLES `t_external` WRITE;
/*!40000 ALTER TABLE `t_external` DISABLE KEYS */;
INSERT INTO `t_external` VALUES (1,'全球影视剧多语言数据库API系统','模糊查电影信息','WMDB_TV','api.wmdb.tv','http://api.wmdb.tv/api/v1/movie/search','GET','q,limit,skip,lang,year','Y','全文模糊搜索，根据匹配分数排序,q为必填，其他为选填，limit和skip用于分页，lang用于返回指定语言的查询数据，支持Cn或者En，year支持限定年份，例如q=英雄&year=2002，则只会返回张艺谋导演、李连杰主演的那一部英雄，可用于各种使用片名+年份精准获取数据的场景！\n','2022-03-02 18:07:59');
/*!40000 ALTER TABLE `t_external` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_film`
--

DROP TABLE IF EXISTS `t_film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_film` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `film_uid` varchar(200) NOT NULL COMMENT '唯一uid',
  `film_status` varchar(100) NOT NULL DEFAULT 'Y' COMMENT '状态 Y正常 N暂停 D删除',
  `film_name` varchar(200) NOT NULL COMMENT '名称',
  `film_original_name` varchar(200) NOT NULL COMMENT '原名',
  `film_alias` varchar(200) NOT NULL COMMENT '别名',
  `film_genre` varchar(150) NOT NULL COMMENT '类型',
  `film_language` varchar(200) DEFAULT NULL COMMENT '语言',
  `film_duration` int(6) DEFAULT NULL COMMENT '电影时长单位分钟',
  `film_scenarist_id` varchar(200) DEFAULT NULL COMMENT '编剧',
  `film_starring_id` varchar(200) DEFAULT NULL COMMENT '主演',
  `film_publish_country` varchar(200) DEFAULT NULL COMMENT '发行地区',
  `film_publish_time` datetime DEFAULT NULL COMMENT '发行时间',
  `film_lang` varchar(100) DEFAULT NULL COMMENT '发行地区简称',
  `film_url` varchar(255) DEFAULT NULL COMMENT 'url',
  `film_poster` varchar(255) NOT NULL COMMENT '电影封面',
  `film_shareImage` varchar(255) DEFAULT NULL COMMENT '卡片',
  `film_description` varchar(255) DEFAULT NULL COMMENT '简介',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  `film_director` varchar(255) DEFAULT NULL COMMENT '导演',
  `film_play_count` int(11) DEFAULT '0' COMMENT '视频播放次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film`
--

LOCK TABLES `t_film` WRITE;
/*!40000 ALTER TABLE `t_film` DISABLE KEYS */;
INSERT INTO `t_film` VALUES (1,'5f968bfcee3680299115bbe6','Y','肖申克的救赎','肖申克的救赎','The Shawshank Redemption','GENRE_FILM','英语',120,'1,5','2,3,4','美国','1994-09-10 00:00:00','cn',NULL,'https://wmdb.querydata.org/movie/poster/1603701754760-c50d8a.jpg','https://wmdb.querydata.org/movie/poster/1605355459683-5f968bfaee3680299115bb97.png','20世纪40年代末，小有成就的青年银行家安迪（蒂姆·罗宾斯 Tim Robbins 饰）因涉嫌杀害妻子及她的情人而锒铛入狱。在这座名为鲨堡的监狱内，希望似乎虚无缥缈，终身监禁的惩罚无疑注定了安迪接下来...','2022-02-24 15:32:28','zhangyu','1',2988987);
/*!40000 ALTER TABLE `t_film` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_film_image`
--

DROP TABLE IF EXISTS `t_film_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_film_image` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `img_uid` varchar(255) NOT NULL COMMENT '唯一标识',
  `img_name` varchar(255) DEFAULT NULL COMMENT '展示名称[系统生成]',
  `img_original` varchar(255) DEFAULT NULL COMMENT '原名',
  `img_status` varchar(255) NOT NULL DEFAULT 'Y' COMMENT '状态 Y正常 N暂停 D删除',
  `img_type` varchar(255) DEFAULT NULL COMMENT '归类',
  `img_suffix` varchar(255) DEFAULT NULL COMMENT '后缀',
  `img_size_level` int(4) DEFAULT NULL COMMENT '图片像素 水平',
  `img_size_plumb` int(4) DEFAULT NULL COMMENT '图片像素 垂直',
  `img_remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film_image`
--

LOCK TABLES `t_film_image` WRITE;
/*!40000 ALTER TABLE `t_film_image` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_film_image` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_film_menu`
--

DROP TABLE IF EXISTS `t_film_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_film_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parent_id` int(11) NOT NULL DEFAULT '0' COMMENT '菜单父ID 顶级0',
  `menu_name` varchar(255) NOT NULL COMMENT '菜单名称',
  `menu_mark` varchar(255) NOT NULL COMMENT '唯一标识',
  `menu_status` varchar(255) NOT NULL DEFAULT 'Y' COMMENT '状态 Y正常 N暂停 D删除',
  `menu_sequence` int(4) DEFAULT NULL COMMENT '排序',
  `menu_icon_url` varchar(255) DEFAULT NULL COMMENT '图标地址',
  `menu_url` varchar(255) DEFAULT NULL COMMENT '跳转地址',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `menu_type` varchar(100) DEFAULT NULL COMMENT '菜单类型',
  `menu_is_child` tinyint(1) DEFAULT '0' COMMENT '是否有子类',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film_menu`
--

LOCK TABLES `t_film_menu` WRITE;
/*!40000 ALTER TABLE `t_film_menu` DISABLE KEYS */;
INSERT INTO `t_film_menu` VALUES (1,0,'首页','','Y',1,NULL,NULL,'2022-02-26 11:11:45','TOP',0),(2,0,'电影','FILM','Y',2,NULL,NULL,'2022-02-26 11:13:20','TOP',0),(3,0,'剧集','EPISODE','Y',3,NULL,NULL,'2022-02-26 11:14:14','TOP',0),(4,0,'综艺','VARIETY','Y',4,NULL,NULL,'2022-02-26 11:15:17','TOP',0),(5,0,'动漫','CARTOON','Y',5,NULL,NULL,'2022-02-26 11:16:02','TOP',0),(6,0,'美剧','USDRAMA','Y',6,NULL,NULL,'2022-02-26 11:17:08','TOP',0),(7,0,'音乐','MUSICAL','Y',7,NULL,NULL,'2022-02-26 11:20:53','TOP',0),(8,0,'壁纸','WALLPAPER','Y',8,NULL,NULL,'2022-02-26 16:52:06','TOP',0),(9,0,'直播','LIVESTREAMING','Y',9,NULL,NULL,'2022-02-26 16:53:06','TOP',0),(10,0,'资讯','INFORMATION','Y',10,NULL,NULL,'2022-02-26 16:54:11','TOP',0),(11,0,'电影','FILM_MENU_ASSORT','Y',1,NULL,NULL,'2022-02-26 21:04:19','FILM_MENU',1),(12,0,'剧集','FILM_MENU_EPISODE','Y',2,NULL,NULL,'2022-02-26 21:05:01','FILM_MENU',1),(13,0,'综艺','FILM_MENU_VARIETY','Y',3,NULL,NULL,'2022-02-27 09:19:07','FILM_MENU',1),(14,0,'动漫','FILM_MENU_CARTOON','Y',4,NULL,NULL,'2022-02-27 09:19:59','FILM_MENU',1),(15,11,'喜剧','COMEDY','Y',1,NULL,NULL,'2022-02-27 09:21:26','FILM_MENU',0),(16,11,'爱情','LOVE','Y',2,NULL,NULL,'2022-02-27 09:26:20','FILM_MENU',0),(17,11,'动作','ACTION','Y',3,NULL,NULL,'2022-02-27 09:26:20','FILM_MENU',0),(18,11,'恐怖','HORROR','Y',4,NULL,NULL,'2022-02-27 09:26:20','FILM_MENU',0),(19,11,'科幻','SCIENCE_FICTION','Y',5,NULL,NULL,'2022-02-27 09:26:20','FILM_MENU',0),(20,11,'剧情','PLOT_OF_PLAY','Y',6,NULL,NULL,'2022-02-27 09:26:20','FILM_MENU',0),(21,12,'警匪','GANGSTER','Y',1,NULL,NULL,'2022-02-27 09:32:14','FILM_MENU',0),(22,12,'悬疑','SUSPEND','Y',2,NULL,NULL,'2022-02-27 09:32:14','FILM_MENU',0),(23,12,'偶像','IDOL','Y',3,NULL,NULL,'2022-02-27 09:32:14','FILM_MENU',0),(24,12,'都市','METROPOLIS','Y',4,NULL,NULL,'2022-02-27 09:32:14','FILM_MENU',0),(25,12,'军事','MILITARY','Y',5,NULL,NULL,'2022-02-27 09:32:14','FILM_MENU',0),(26,12,'古装','ANCIENT_COSTUME','Y',6,NULL,NULL,'2022-02-27 09:32:14','FILM_MENU',0),(27,13,'选秀','DRAFT','Y',1,NULL,NULL,'2022-02-27 09:37:51','FILM_MENU',0),(28,13,'搞笑','FUNNY','Y',2,NULL,NULL,'2022-02-27 09:37:51','FILM_MENU',0),(29,13,'访谈','INTERVIEW','Y',3,NULL,NULL,'2022-02-27 09:37:51','FILM_MENU',0),(30,13,'体育','SPORTS','Y',4,NULL,NULL,'2022-02-27 09:37:51','FILM_MENU',0),(31,13,'纪实','DOCUMENTARY','Y',5,NULL,NULL,'2022-02-27 09:37:51','FILM_MENU',0),(32,13,'科教','SCIENCE_EDUCATION','Y',6,NULL,NULL,'2022-02-27 09:37:51','FILM_MENU',0),(33,14,'热血','BLOOD','Y',1,NULL,NULL,'2022-02-27 09:44:37','FILM_MENU',0),(34,14,'恋爱','AMATIVENESS','Y',2,NULL,NULL,'2022-02-27 09:44:37','FILM_MENU',0),(35,14,'校园','CAMPUS','Y',3,NULL,NULL,'2022-02-27 09:44:37','FILM_MENU',0),(36,14,'幻想','FANTASY','Y',4,NULL,NULL,'2022-02-27 09:44:37','FILM_MENU',0),(37,14,'悬疑','CARTOON_SUSPEND','Y',5,NULL,NULL,'2022-02-27 09:44:37','FILM_MENU',0),(38,14,'成人','ADULT','Y',6,NULL,NULL,'2022-02-27 09:44:37','FILM_MENU',0);
/*!40000 ALTER TABLE `t_film_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_film_score`
--

DROP TABLE IF EXISTS `t_film_score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_film_score` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `score_platform` varchar(255) DEFAULT NULL COMMENT '评分平台名称',
  `score_platform_icon` varchar(255) DEFAULT NULL COMMENT '平台展示图标',
  `score_total` int(255) DEFAULT NULL COMMENT '评分总次数',
  `score_ratio` decimal(6,2) DEFAULT NULL COMMENT '评分',
  `sequence` int(4) DEFAULT NULL COMMENT '展示排序',
  `film_uid` varchar(200) NOT NULL COMMENT '视频uID',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film_score`
--

LOCK TABLES `t_film_score` WRITE;
/*!40000 ALTER TABLE `t_film_score` DISABLE KEYS */;
INSERT INTO `t_film_score` VALUES (1,'豆瓣评分',NULL,2170679,9.70,1,'5f968bfcee3680299115bbe6','2022-02-24 16:02:30'),(2,'IMDB评分',NULL,2297852,9.30,2,'5f968bfcee3680299115bbe6','2022-02-24 16:03:13'),(3,'烂番茄指数',NULL,75,9.10,3,'5f968bfcee3680299115bbe6','2022-02-24 16:03:57'),(4,'xx评分',NULL,8586,9.60,4,'5f968bfcee3680299115bbe6','2022-02-25 16:22:22');
/*!40000 ALTER TABLE `t_film_score` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_film_type`
--

DROP TABLE IF EXISTS `t_film_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_film_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(255) DEFAULT NULL COMMENT '类型名称',
  `type_mark` varchar(255) NOT NULL COMMENT '类型标识唯一',
  `type_reveal_name` varchar(255) DEFAULT NULL COMMENT '展示类型名称',
  `type_assort` varchar(100) NOT NULL COMMENT '类型归类：普通类型TYPE；电影榜类型NOTICE',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film_type`
--

LOCK TABLES `t_film_type` WRITE;
/*!40000 ALTER TABLE `t_film_type` DISABLE KEYS */;
INSERT INTO `t_film_type` VALUES (1,'犯罪','CRIME','犯罪','TYPE'),(2,'剧情','DRAMA','剧情','TYPE');
/*!40000 ALTER TABLE `t_film_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_person_info`
--

DROP TABLE IF EXISTS `t_person_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_person_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `ch_name` varchar(255) DEFAULT NULL COMMENT '中文名',
  `en_name` varchar(255) DEFAULT NULL COMMENT '外文名',
  `hometown` varchar(255) DEFAULT NULL COMMENT '籍贯',
  `born_time` datetime DEFAULT NULL COMMENT '出生日期',
  `height` int(4) DEFAULT NULL COMMENT '身高',
  `ethnic` varchar(200) DEFAULT NULL COMMENT '民族',
  `profession` varchar(255) DEFAULT NULL COMMENT '职业',
  `recent_photos` varchar(255) DEFAULT NULL COMMENT '近期照片',
  `film_profession` varchar(100) DEFAULT NULL COMMENT '视频职业 SCENARIST:编剧 STAR:主演',
  `film_uid` varchar(200) DEFAULT NULL COMMENT '视频uid',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_person_info`
--

LOCK TABLES `t_person_info` WRITE;
/*!40000 ALTER TABLE `t_person_info` DISABLE KEYS */;
INSERT INTO `t_person_info` VALUES (1,'弗兰克·达拉邦特','Frank A. Darabont','法国,杜省,蒙贝利亚尔','1959-01-28 00:00:00',175,NULL,'编剧/导演/制片人/演员','https://img3.doubanio.com/view/celebrity/raw/public/p230.jpg','DIRECTOR,SCENARIST','5f968bfcee3680299115bbe6','2022-02-24 15:41:23','zhangyu'),(2,'蒂姆·罗宾斯','Tim Robbins','美国,加利福尼亚州,西科维纳','1958-10-16 00:00:00',180,NULL,'演员/导演/制片人/编剧/配音','https://img9.doubanio.com/view/celebrity/raw/public/p17525.jpg','STAR','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu'),(3,'摩根·弗里曼 Morgan Freeman','Morgan Porterfield Freeman Jr.','美国,田纳西州,孟菲斯','1937-06-01 00:00:00',180,NULL,'演员/制片人/配音/导演/主持人','https://img2.doubanio.com/view/celebrity/raw/public/p34642.jpg','STAR','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu'),(4,'威廉姆·赛德勒 William Sadler','William Thomas Sadler','美国,纽约,布法罗','1950-04-13 00:00:00',185,NULL,'演员/导演','https://img1.doubanio.com/view/celebrity/raw/public/p7827.jpg','STAR','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu'),(5,'斯蒂芬·金 Stephen King','Stephen Edwin King','美国,缅因,波特兰','1947-09-21 00:00:00',175,NULL,'编剧/演员/制片人/配音/导演','https://img9.doubanio.com/view/celebrity/raw/public/p1359443605.4.jpg','SCENARIST','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu');
/*!40000 ALTER TABLE `t_person_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_play_record`
--

DROP TABLE IF EXISTS `t_play_record`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_play_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `play_film_uid` varchar(200) DEFAULT NULL COMMENT '播放的电影',
  `play_time` datetime DEFAULT NULL COMMENT '播放时间',
  `play_account` datetime DEFAULT NULL COMMENT '播放时间',
  `play_duration` int(11) DEFAULT NULL COMMENT '播放时长',
  `play_ip` varchar(20) DEFAULT NULL COMMENT '客户端IP',
  `film_name` varchar(200) DEFAULT NULL COMMENT '电影名称',
  `film_url` varchar(200) DEFAULT NULL COMMENT '跳转地址',
  `creact_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `record_status` int(4) DEFAULT '1' COMMENT '播放记录状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_play_record`
--

LOCK TABLES `t_play_record` WRITE;
/*!40000 ALTER TABLE `t_play_record` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_play_record` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `t_type_relation_film`
--

DROP TABLE IF EXISTS `t_type_relation_film`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `t_type_relation_film` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_id` int(11) NOT NULL COMMENT '类型标识唯一',
  `film_uid` varchar(200) NOT NULL COMMENT '视频唯一UID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_type_relation_film`
--

LOCK TABLES `t_type_relation_film` WRITE;
/*!40000 ALTER TABLE `t_type_relation_film` DISABLE KEYS */;
/*!40000 ALTER TABLE `t_type_relation_film` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-02 19:00:10
