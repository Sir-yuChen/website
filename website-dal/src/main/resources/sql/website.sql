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
  `film_genre_id` varchar(150) NOT NULL COMMENT '类型',
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film`
--

LOCK TABLES `t_film` WRITE;
/*!40000 ALTER TABLE `t_film` DISABLE KEYS */;
INSERT INTO `t_film` (`id`, `film_uid`, `film_status`, `film_name`, `film_original_name`, `film_alias`, `film_genre_id`, `film_language`, `film_duration`, `film_scenarist_id`, `film_starring_id`, `film_publish_country`, `film_publish_time`, `film_lang`, `film_url`, `film_poster`, `film_shareImage`, `film_description`, `creact_time`, `creator`, `film_director`) VALUES (1,'5f968bfcee3680299115bbe6','Y','肖申克的救赎','肖申克的救赎','The Shawshank Redemption','1,2','英语',120,'1,5','2,3,4','美国','1994-09-10 00:00:00','cn',NULL,'https://wmdb.querydata.org/movie/poster/1603701754760-c50d8a.jpg','https://wmdb.querydata.org/movie/poster/1605355459683-5f968bfaee3680299115bb97.png','20世纪40年代末，小有成就的青年银行家安迪（蒂姆·罗宾斯 Tim Robbins 饰）因涉嫌杀害妻子及她的情人而锒铛入狱。在这座名为鲨堡的监狱内，希望似乎虚无缥缈，终身监禁的惩罚无疑注定了安迪接下来...','2022-02-24 15:32:28','zhangyu','1');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film_menu`
--

LOCK TABLES `t_film_menu` WRITE;
/*!40000 ALTER TABLE `t_film_menu` DISABLE KEYS */;
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
INSERT INTO `t_film_score` (`id`, `score_platform`, `score_platform_icon`, `score_total`, `score_ratio`, `sequence`, `film_uid`, `creact_time`) VALUES (1,'豆瓣评分',NULL,2170679,9.70,1,'5f968bfcee3680299115bbe6','2022-02-24 16:02:30'),(2,'IMDB评分',NULL,2297852,9.30,2,'5f968bfcee3680299115bbe6','2022-02-24 16:03:13'),(3,'烂番茄指数',NULL,75,9.10,3,'5f968bfcee3680299115bbe6','2022-02-24 16:03:57'),(4,'xx评分',NULL,8586,9.60,4,'5f968bfcee3680299115bbe6','2022-02-25 16:22:22');
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `t_film_type`
--

LOCK TABLES `t_film_type` WRITE;
/*!40000 ALTER TABLE `t_film_type` DISABLE KEYS */;
INSERT INTO `t_film_type` (`id`, `type_name`, `type_mark`, `type_reveal_name`) VALUES (1,'犯罪','CRIME','犯罪'),(2,'剧情','DRAMA','剧情');
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
INSERT INTO `t_person_info` (`id`, `ch_name`, `en_name`, `hometown`, `born_time`, `height`, `ethnic`, `profession`, `recent_photos`, `film_profession`, `film_uid`, `creact_time`, `creator`) VALUES (1,'弗兰克·达拉邦特','Frank A. Darabont','法国,杜省,蒙贝利亚尔','1959-01-28 00:00:00',175,NULL,'编剧/导演/制片人/演员','https://img3.doubanio.com/view/celebrity/raw/public/p230.jpg','DIRECTOR,SCENARIST','5f968bfcee3680299115bbe6','2022-02-24 15:41:23','zhangyu'),(2,'蒂姆·罗宾斯','Tim Robbins','美国,加利福尼亚州,西科维纳','1958-10-16 00:00:00',180,NULL,'演员/导演/制片人/编剧/配音','https://img9.doubanio.com/view/celebrity/raw/public/p17525.jpg','STAR','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu'),(3,'摩根·弗里曼 Morgan Freeman','Morgan Porterfield Freeman Jr.','美国,田纳西州,孟菲斯','1937-06-01 00:00:00',180,NULL,'演员/制片人/配音/导演/主持人','https://img2.doubanio.com/view/celebrity/raw/public/p34642.jpg','STAR','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu'),(4,'威廉姆·赛德勒 William Sadler','William Thomas Sadler','美国,纽约,布法罗','1950-04-13 00:00:00',185,NULL,'演员/导演','https://img1.doubanio.com/view/celebrity/raw/public/p7827.jpg','STAR','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu'),(5,'斯蒂芬·金 Stephen King','Stephen Edwin King','美国,缅因,波特兰','1947-09-21 00:00:00',175,NULL,'编剧/演员/制片人/配音/导演','https://img9.doubanio.com/view/celebrity/raw/public/p1359443605.4.jpg','SCENARIST','5f968bfcee3680299115bbe6','2022-02-24 15:45:33','zhangyu');
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

-- Dump completed on 2022-02-25 17:29:50
