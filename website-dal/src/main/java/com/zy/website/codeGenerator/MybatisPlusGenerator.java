package com.zy.website.codeGenerator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Description: mybatis plus 代码生成器
 */
public class MybatisPlusGenerator {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
//        {"t_film","t_film_score","t_film_type","t_person_info"}
//        {"t_dic_data","t_dictionary","t_film_image","t_film_menu","t_play_record","t_type_relation_film"}
        String [] tableNames = new String[]{}; //需要生成的表
        String [] excludeTableNames = new String[]{};//需要排除的表
        String author = "zhangyu";//用户名
        String dataSourceUrl = "jdbc:mysql://127.0.0.1:3306/website?userUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC";//数据源地址
        String dataSourceDriver = "com.mysql.cj.jdbc.Driver";
        String dataSourceUsername = "root";
        String dataSourcePassword = "123456";
        String tablePrefix = "t_"; //表前缀


        AutoGenerator mpg = new AutoGenerator();// 代码生成器
        GlobalConfig gc = new GlobalConfig();// 全局配置
        gc.setAuthor(author);//用户名
        gc.setOpen(false); //是否打开输出的目录
        gc.setFileOverride(true);//是否重写
        gc.setActiveRecord(true);// 开启 activeRecord 模式
        gc.setDateType(DateType.ONLY_DATE);//设置生成实体日期属性的类型,我这里用的是util中的Date
        //gc.setSwagger2(true);  //带Swagger2注释
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// mapper.xml生成查询映射结果
        gc.setBaseColumnList(true);//mapper.xml生成查询结果列
        gc.setMapperName("%sMapper");
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setEntityName("%sModel");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);//数据源类型
        dsc.setUrl(dataSourceUrl);
        dsc.setDriverName(dataSourceDriver);
        dsc.setUsername(dataSourceUsername);
        dsc.setPassword(dataSourcePassword);
        mpg.setDataSource(dsc);

        PackageConfig pc = new PackageConfig(); // 包配置
        pc.setParent("com.zy.website");
        pc.setController("controller");
        pc.setEntity("model");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setServiceImpl("service.impl");
        mpg.setPackageInfo(pc);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // TODO: 设置为null时，则不会生成xml文件，controller、service等Java类
        templateConfig.setXml("/templates/mapper.xml")
                .setEntity("/templates/entity.java")		        				// 设置生成entity的模板
                .setMapper("/templates/mapper.java")		        				// 设置生成mapper的模板
                .setController("/templates/controller.java")       				// 设置生成service的模板
                .setService("/templates/service.java")							// 设置生成serviceImpl的模板
                .setServiceImpl("/templates/serviceImpl.java");					// 设置生成controller的模板
        mpg.setTemplate(templateConfig);

//        String templatePath = "/templates/mapper.xml.vm";//velocity 二选一
        String templatePath = "/templates/mapper.xml.ftl"; //freemarker




        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);


        //实体共同继承的基础类, 里面抽取了共同的一些属性
//        strategy.setSuperEntityClass("com.example.open.entity.BaseEntity");
        // 写于父类中的公共字段,一下为生成的实体所忽略的字段
//        strategy.setSuperEntityColumns("ID", "CREATE_TIME", "MODIFY_TIME", "DELETED");
        //逻辑删除属性名称
//        strategy.setLogicDeleteFieldName("DELETED");

        //是否使用lombok注解
        strategy.setEntityLombokModel(true);
        //是否定义输出的实体类有builder模式的调用链
        strategy.setEntityBuilderModel(true);
        //是否生成字段注解:读取表中的注释
        strategy.setEntityTableFieldAnnotationEnable(true);
        //是否生成@RestController注解的controller
        strategy.setRestControllerStyle(true);
        // controller共同继承的公共父类
        strategy.setSuperControllerClass("com.zy.website.controller.BaseController");
        //驼峰转连接字符- , 例如PlatformUser -->  platform-user
        strategy.setControllerMappingHyphenStyle(true);
        //去除表名的前缀的固定生成实体
        strategy.setTablePrefix(tablePrefix);

        //自定义注入属性,在使用单个生成时,可以使用 自定义实体类名称
        InjectionConfig injectionConfig = new InjectionConfig() {
            //自定义属性注入:abc
            //在.ftl(或者是.vm)模板中，通过${cfg.abc}获取属性
            @Override
            public void initMap() {
//                List<TableInfo> list = this.getConfig().getTableInfoList();
//                if (list != null && !list.isEmpty()) {
//                    TableInfo info = list.get(0);
//                    Map<String, Object> map = new HashMap<>();
//                    map.put("serviceImplNamePath", info.getEntityPath() + "Service");
//                    super.setMap(map);
//                }
            }
        };

        mpg.setCfg(injectionConfig);
        strategy.setExclude(excludeTableNames);//不需要生成的表
        strategy.setInclude(tableNames);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new VelocityTemplateEngine());
//        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        //自定义文件生成路径，包路径
        //这里调用customPackagePath方法，使用可以自己在内部灵活配置路径
        //如果不调用该方法、就会使用MyBatis-Plus默认的文件生成路径和包路径生成文件、但可以使用上面的PackageConfig做一些简单的配置
        customPackagePath(pc, mpg);

        mpg.execute();
    }

    /**
     * 自定义包路径，文件生成路径，这边配置更灵活
     * 虽然也可以使用InjectionConfig设置FileOutConfig的方式设置路径
     * 这里直接使用Map方式注入ConfigBuilder配置对象更加直观
     *
     * @param pc
     * @param mpg
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public static void customPackagePath(PackageConfig pc, AutoGenerator mpg) throws NoSuchFieldException, IllegalAccessException {

        String projectPath = System.getProperty("user.dir");
        String mavenPath = "\\src\\main\\java\\";
        /**
         * packageInfo配置controller、service、serviceImpl、entity、mapper等文件的包路径
         * 这里包路径可以根据实际情况灵活配置
         */
        Map<String, String> packageInfo = new HashMap<>();
        packageInfo.put(ConstVal.CONTROLLER, "com.zy.website.controller");
        packageInfo.put(ConstVal.SERVICE, "com.zy.website.service");
        packageInfo.put(ConstVal.SERVICE_IMPL, "com.zy.website.service.impl");
        packageInfo.put(ConstVal.ENTITY, "com.zy.website.model");
        packageInfo.put(ConstVal.MAPPER, "com.zy.website.mapper");

        /**
         * pathInfo配置controller、service、serviceImpl、entity、mapper、mapper.xml等文件的生成路径
         * srcPath也可以更具实际情况灵活配置
         * 后面部分的路径是和上面packageInfo包路径对应的源码文件夹路径
         * 这里你可以选择注释其中某些路径，可忽略生成该类型的文件，例如:注释掉下面pathInfo中Controller的路径，就不会生成Controller文件
         */
        Map pathInfo = new HashMap<>();
        // 路径 父地址 +子
        pathInfo.put(ConstVal.CONTROLLER_PATH, projectPath + "\\website\\website-web\\" + mavenPath + packageInfo.get(ConstVal.CONTROLLER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.SERVICE_PATH, projectPath + "\\website\\website-service\\" + mavenPath + packageInfo.get(ConstVal.SERVICE).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.SERVICE_IMPL_PATH, projectPath + "\\website\\website-service\\" + mavenPath + packageInfo.get(ConstVal.SERVICE_IMPL).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.ENTITY_PATH, projectPath + "\\website\\website-dal\\" + mavenPath + packageInfo.get(ConstVal.ENTITY).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.MAPPER_PATH, projectPath + "\\website\\website-dal\\" + mavenPath + packageInfo.get(ConstVal.MAPPER).replaceAll("\\.", StringPool.BACK_SLASH + File.separator));
        pathInfo.put(ConstVal.XML_PATH, projectPath + "\\website\\website-dal\\src\\main\\resources\\mapper\\");
        pc.setPathInfo(pathInfo);

        /**
         * 创建configBuilder对象，传入必要的参数
         * 将以上的定义的包路径packageInfo配置到赋值到configBuilder对象的packageInfo属性上
         * 因为packageInfo是私有成员变量，也没有提交提供公共的方法，所以使用反射注入
         * 为啥要这么干，看源码去吧
         */
        ConfigBuilder configBuilder = new ConfigBuilder(mpg.getPackageInfo(), mpg.getDataSource(), mpg.getStrategy(), mpg.getTemplate(), mpg.getGlobalConfig());
        Field packageInfoField = configBuilder.getClass().getDeclaredField("packageInfo");
        packageInfoField.setAccessible(true);
        packageInfoField.set(configBuilder, packageInfo);

        /**
         * 设置配置对象
         */
        mpg.setConfig(configBuilder);
    }


}
