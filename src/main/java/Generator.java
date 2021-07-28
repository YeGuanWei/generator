import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * MP更新日志
 * <p>
 * 生成代码
 * </p>
 *
 * @author 叶关伟
 * @since 2021-07-28
 */
public class Generator {

    // 数据库类型
    public static DbType dbType = DbType.POSTGRE_SQL;
    // 数据库驱动
    public static String dbDriverName = "org.postgresql.Driver";
    // 数据库地址
    public static String dbUrl = "";
    // 数据库名称
    public static String dbUserName = "";
    // 数据库密码
    public static String dbPassword = "";
    // 输出文件夹
    public static String outputDir = "./demo";
    // 实体类的作者
    public static String author = "demo";
    // 数据库中实体类的表头(定义之后会自动去掉这些表头)
    String tablePrefix[] = {"sys_",""};


    public static void main(String[] args) {
        Generator gen = new Generator();
        gen.generateCode();
    }

    public void generateCode() {
        // 项目包名
        String packageName = "cn.demo";

        // 重设Service名称
        // 当为false时: user -> UserService,
        // 当为true时: user -> IUserService
        boolean serviceNameStartWithI = true;

        //模块 - 功能组划分 - 表列表
        Map<String, Map<String, String[]>> tables = new HashMap<String, Map<String, String[]>>() {
            {
                put("", new HashMap<String, String[]>() {{
                    put("", new String[] {
                        "contract_approval",
                    });
                }});
            }
        };

        for (String key: tables.keySet()) {
            Map<String, String[]> module = tables.get(key);

            for (String group : module.keySet()) {
                generateByTables(serviceNameStartWithI, packageName + key, true, group, module.get(group));
                generateByTables(serviceNameStartWithI, packageName + key, false, group, module.get(group));
            }
        }

    }

    private void generateByTables(boolean serviceNameStartWithI, String packageName, boolean isDto, String group, String... tableNames) {

        GlobalConfig config = new GlobalConfig();
        config.setActiveRecord(false)
                .setBaseResultMap(true)
                .setBaseColumnList(true)
                .setSwagger2(true)
                .setAuthor(author)
                .setOutputDir(outputDir)
                .setFileOverride(true);
        if (!serviceNameStartWithI) {
            config.setServiceName("%sService");
        }
        if (isDto) {
            config.setEntityName("%sDTO");
        }

        DataSourceConfig dataSourceConfig = new OverrideDataSourceConfig();
        dataSourceConfig.setDbType(dbType)
                .setUrl(dbUrl)
                .setUsername(dbUserName)
                .setPassword(dbPassword)
                .setDriverName(dbDriverName);

        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig
                .setTablePrefix(tablePrefix)
                .setCapitalMode(true)
                .setEntityLombokModel(true)
                .setEntityBuilderModel(isDto ? false : true)
                .setEntityColumnConstant(isDto ? false : true)
                .setRestControllerStyle(true)
                .entityTableFieldAnnotationEnable(false)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setInclude(tableNames);

        PackageConfig packageConfig = new PackageConfig()
                .setParent(packageName)
                .setEntity("model." + (isDto ? "dto" : "entity"));

        if (group != null && !group.trim().equals(StringPool.EMPTY)) {
            group = group.trim();

            packageConfig.setController(packageConfig.getController() + StringPool.DOT + group)
                    .setEntity(packageConfig.getEntity() + StringPool.DOT + group)
                    .setService(packageConfig.getService() + StringPool.DOT + group)
                    .setServiceImpl(packageConfig.getServiceImpl() + StringPool.DOT + group)
                    .setXml(packageConfig.getMapper() + StringPool.DOT + "mybatis-mapping" + StringPool.DOT + group)
                    .setMapper(packageConfig.getMapper() + StringPool.DOT + group);
        } else {
            packageConfig.setXml(packageConfig.getMapper() + StringPool.DOT + "mybatis-mapping");
        }

        new AutoGenerator().setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig)
                .execute();

    }

}