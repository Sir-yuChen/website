package com.zy.website.codeGenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**************************************************************
 * 功能描述:  mybatis plus代码生成器
 **************************************************************/
public class CodeAutoGenerator {

    /**
     * 作者
     */
    private static String AUTHOR = "zhangyu";
    /**
     * 生成的实体类忽略表前缀: 不需要则置空
     */
    private static String ENTITY_IGNORE_PREFIX = "t_";
    /**
     * 表名
     */
    private static String[] TABLES = {
            "t_user",
    };


    // 各层文件输出到模块, 没有则置空
    /**
     * Entity.java, Mapper.java, Mapper.xml输出模块路径
     */
    private static String DAO_OUTPUT_MODULE = "/website/website-dal";
    /**
     * mapper.xml输出模块路径(需要注意放置的位置:默认从模块/src/main下开始)
     */
    private static String XML_OUTPUT_MODULE = "/resources/mapper";
    /**
     * IService.java, serviceImpl.java输出模块路径
     */
    private static String SERVICE_OUTPUT_MODULE = "/website/website-service";
    /**
     * Controller.java输出模块路径
     */
    private static String Controller_OUTPUT_MODULE = "/website/website-web";


    /**
     * 父包名路径(文件输出路径,也是导包的路径)
     */
    private static String PARENT_PACKAGE_PATH = "/com/zy/website";

    // 各层包名
    private static String ENTITY_PATH = "/model/";
    private static String MAPPER_PATH = "/mapper/";
    private static String XML_PATH = "/resources/mapper/";
    private static String SERVICE_PATH = "/service/";
    private static String SERVICE_IMPL_PATH = "/service/impl/";
    private static String CONTROLLER_PATH = "/controller/";


    // 数据库
    private static String username = "root";
    private static String password = "123456";
    private static String url = "jdbc:mysql://127.0.0.1:3306/backstage?userUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";
    private static DbType DB_TYPE = DbType.MYSQL;
    private static String driverClassName = "com.mysql.cj.jdbc.Driver";


    // 自定义输出模板和位置
    // 文件位置输出模式: file output path = projectPath + XX_OUTPUT_PATH  + File
    // XX_OUTPUT_PATH = modulePath + packagePath
    /**
     * entity输出模板
     */
    private static String ENTITY_TEMPLATE = "templates/entity.java.ftl";
    private static String ENTITY_OUTPUT_PATH = DAO_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH  + ENTITY_PATH;
    /**
     * mapper.xml输出模板
     */
    private static String XML_TEMPLATE = "templates/mapper.xml.ftl";
    private static String XML_OUTPUT_PATH = DAO_OUTPUT_MODULE + "/src/main" + XML_PATH;
    /**
     * mapper.java输出模板
     */
    private static String MAPPER_TEMPLATE = "templates/mapper.java.ftl";
    private static String MAPPER_OUTPUT_PATH = DAO_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH  + MAPPER_PATH;
    /**
     * service输出模板
     */
    private static String SERVICE_TEMPLATE = "templates/service.java.ftl";
    private static String SERVICE_OUTPUT_PATH = SERVICE_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH  + SERVICE_PATH;
    /**
     * serviceImpl输出模板
     */
    private static String SERVICE_IMPL_TEMPLATE = "templates/serviceImpl.java.ftl";
    private static String SERVICE_IMPL_OUTPUT_PATH = SERVICE_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH  + SERVICE_IMPL_PATH;
    /**
     * controller输出模板
     */
    private static String CONTROLLER_TEMPLATE = "templates/controller.java.ftl";
    private static String CONTROLLER_OUTPUT_PATH = Controller_OUTPUT_MODULE + "/src/main/java" + PARENT_PACKAGE_PATH + CONTROLLER_PATH;


    public static void main(String[] args) {

        // 全局配置
        GlobalConfig globalConfig = globalConfig();
        // 数据源配置
        DataSourceConfig dataSourceConfig = dataSourceConfig();
        // 策略配置
        StrategyConfig strategyConfig = strategyConfig();
        // 包配置
        PackageConfig packageConfig = packageConfig();
        // 模板配置
        TemplateConfig templateConfig = templateConfig();
        // 自定义配置
        InjectionConfig injectionConfig = injectionConfig();

        // 执行
        new AutoGenerator().setGlobalConfig(globalConfig)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                // 因为使用了自定义模板,所以需要把各项置空否则会多生成一次
                .setTemplate(templateConfig)
                // 使用的模板引擎，如果不是默认模板引擎则需要添加模板依赖到pom
                .setTemplateEngine(new FreemarkerTemplateEngine())
                .setCfg(injectionConfig)
                .execute();
    }

    /**
     * 全局配置
     */
    private static GlobalConfig globalConfig() {
        return new GlobalConfig()
                // 打开文件
                .setOpen(false)
                // 文件覆盖
                .setFileOverride(true)
                // 开启activeRecord模式
                .setActiveRecord(true)
                // XML ResultMap: mapper.xml生成查询映射结果
                .setBaseResultMap(true)
                // XML ColumnList: mapper.xml生成查询结果列
                .setBaseColumnList(true)
                // swagger注解; 须添加swagger依赖
//                .setSwagger2(true)
                // 作者
                .setAuthor(AUTHOR)
                .setMapperName("%sMapper")
                .setControllerName("%sController")
                .setServiceName("%sService")
                .setServiceImplName("%sServiceImpl")
                .setEntityName("%sModel");
    }

    /**
     * 数据源配置
     */
    private static DataSourceConfig dataSourceConfig() {
        return new DataSourceConfig()
                // 数据库类型
                .setDbType(DB_TYPE)
                // 连接驱动
                .setDriverName(driverClassName)
                // 地址
                .setUrl(url)
                // 用户名
                .setUsername(username)
                // 密码
                .setPassword(password);
    }

    /**
     * 策略配置
     */
    private static StrategyConfig strategyConfig() {
        return new StrategyConfig()
                // 表名生成策略：下划线连转驼峰
                .setNaming(NamingStrategy.underline_to_camel)
                // 表字段生成策略：下划线连转驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel)
                // 需要生成的表
                .setInclude(TABLES)
                // 生成controller
                .setRestControllerStyle(true)
                // 去除表前缀
                .setTablePrefix(ENTITY_IGNORE_PREFIX)
                // controller映射地址：驼峰转连字符
                .setControllerMappingHyphenStyle(true)
                // 是否启用builder 模式
                .setEntityBuilderModel(true)
                // 是否为lombok模型; 需要lombok依赖
                .setEntityLombokModel(true)
                // 生成实体类字段注解
                .setEntityTableFieldAnnotationEnable(true);
    }

    /**
     * 包配置
     * 设置包路径用于导包时使用，路径示例：com.path
     */
    private static PackageConfig packageConfig() {

        String entity = DAO_OUTPUT_MODULE + ENTITY_PATH;
        String mapper = DAO_OUTPUT_MODULE + MAPPER_PATH;
        String xml = DAO_OUTPUT_MODULE + XML_PATH;
        String service = SERVICE_OUTPUT_MODULE + SERVICE_PATH;
        String serviceImpl = SERVICE_OUTPUT_MODULE + SERVICE_IMPL_PATH;
        String controller = Controller_OUTPUT_MODULE + CONTROLLER_PATH;

        return new PackageConfig()
                // 父包名
                .setParent(PARENT_PACKAGE_PATH.replace('/', '.').substring(1))
                .setEntity(entity.replace('/', '.').substring(1, entity.length() - 1))
                .setMapper(mapper.replace('/', '.').substring(1, mapper.length() - 1))
                .setXml(xml.replace('/', '.').substring(1, xml.length() - 1))
                .setService(service.replace('/', '.').substring(1, service.length() - 1))
                .setServiceImpl(serviceImpl.replace('/', '.').substring(1, serviceImpl.length() - 1))
                .setController(controller.replace('/', '.').substring(1, controller.length() - 1));
    }

    /**
     * 模板配置
     */
    private static TemplateConfig templateConfig() {
        return new TemplateConfig()
                // 置空后方便使用自定义输出位置
                .setEntity(null)
                .setXml(null)
                .setMapper(null)
                .setService(null)
                .setServiceImpl(null)
                .setController(null);
    }

    /**
     * 自定义配置
     */
    private static InjectionConfig injectionConfig() {
        return new InjectionConfig() {
            @Override
            public void initMap() {
                // 注入配置
            }
        }
                // 判断是否创建文件
                .setFileCreate(new IFileCreate() {
                    @Override
                    public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {

                        // 检查文件目录，不存在自动递归创建
                        checkDir(filePath);

                        // 指定需要覆盖的文件
                        // 文件结尾名字参照 全局配置 中对各层文件的命名,未修改为默认值
                        if (isExists(filePath) && (!filePath.endsWith("Mapper.xml") && !filePath.endsWith("Dao.java") && !filePath.endsWith("Mapper.java"))) {
                            return false;
                        }

                        return true;
                    }
                })
                // 自定义输出文件
                .setFileOutConfigList(fileOutConfigList());
    }

    /**
     * 自定义输出文件配置
     */
    private static List<FileOutConfig> fileOutConfigList() {
        List<FileOutConfig> list = new ArrayList<>();
        // 当前项目路径
        String projectPath = System.getProperty("user.dir");

        // 实体类文件输出
        list.add(new FileOutConfig(ENTITY_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + ENTITY_OUTPUT_PATH + tableInfo.getEntityName() + StringPool.DOT_JAVA;
            }
        });
        // mapper xml文件输出
        list.add(new FileOutConfig(XML_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + XML_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_XML;
            }
        });
        // mapper文件输出
        list.add(new FileOutConfig(MAPPER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + MAPPER_OUTPUT_PATH + tableInfo.getMapperName() + StringPool.DOT_JAVA;
            }
        });
        // service文件输出
        list.add(new FileOutConfig(SERVICE_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + SERVICE_OUTPUT_PATH + tableInfo.getServiceName() + StringPool.DOT_JAVA;
            }
        });
        // service impl文件输出
        list.add(new FileOutConfig(SERVICE_IMPL_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + SERVICE_IMPL_OUTPUT_PATH + tableInfo.getServiceImplName() + StringPool.DOT_JAVA;
            }
        });
        // controller文件输出
        list.add(new FileOutConfig(CONTROLLER_TEMPLATE) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + CONTROLLER_OUTPUT_PATH + tableInfo.getControllerName() + StringPool.DOT_JAVA;
            }
        });

        return list;
    }

    /**
     * 判断文件是否存在
     *
     * @param path 路径
     * @return
     */
    private static boolean isExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}