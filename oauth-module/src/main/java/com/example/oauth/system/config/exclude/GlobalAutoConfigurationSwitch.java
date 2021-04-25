package com.example.oauth.system.config.exclude;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.util.*;

/**
 * <p>
 *     本类主要实现的功能如下:
 *     boot 项目启动加载 spring.factories 配置文件阶段，会来执行该类，因为在该项目 META-INF/spring.factories 中，配置了
 *     org.springframework.boot.env.EnvironmentPostProcessor={@code this.getClass().getName}，在该类中，主要是完成
 *     classpath:autoExcludeConfig.yml 文件的读取和解析，当 prefix=cm.module.enable 的 key 设置为 false 的情况
 *     则表示该模块对应的自动配置类将会在 springboot 中被排除加载
 *
 *     eg: cm.module.enable.redis=false，那么本项目中 Redis 的配置将不会生效，也就是本项目中不会使用该中间件
 *
 *     后续的处理逻辑则是在 {@code AutoConfigurationImportSelector#getExcludeAutoConfigurationsProperty()} 内完成的，
 *     此方法，主要是通过读取 {@link Environment} 类，来寻找所有配置为 {@see spring.autoconfigure.exclude} 的自动配置，
 *     最终会执行 {@code org.springframework.boot.autoconfigure.AutoConfigurationImportSelector#getAutoConfigurationEntry(AnnotationMetadata)}
 *     方法，然后在方法内，获取到扫描出来的全部自动配置项，在需要自动配置项类，去移除调不被自动配置的类，这样本类目的就达到了
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Slf4j
public class GlobalAutoConfigurationSwitch extends AutoConfigurationSwitchSupport implements EnvironmentPostProcessor {

    /**
     *  {@code org.springframework.boot.autoconfigure.AutoConfigurationImportSelector#PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE}
     *  该配置前缀，主要是 spring boot 针对自动配置项进行移除的关键配置，如果 spring boot 中配置了该项，那么将会在 boot 项目初始化
     *  读取配置文件之后，来扫描该配置 {@see org.springframework.boot.autoconfigure.AutoConfigurationImportSelector#getExclu
     *  -deAutoConfigurationsProperty()}，该方法主要作用是通过对全局 Environment 对象的扫描，读取 {@code PROPERTY_NAME_AUTOCONFIGURE
     *  _EXCLUDE} 的所有字符串，最终返回一个 {@link List<String>} 的，被排除的自动配置类全路径
     **/
    private static final String PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE = "spring.autoconfigure.exclude";

    /**
     * 字符串切分正则表达式，切分目的 cm.module.enable.redis  -> 切分为  redis, 从 {@see definitionExcludeContainers} 结果中获取全路径
     */
    public static final String REGEX_COMMAND = "\\.";

    /**
     *  被扫描到所有配置为 false 的自动配置类，将会被添加到 {@see excludeContainer} 中，但是环境变量 {@see ConfigurableEnvironment} 内
     *  包含的所有被移除的类却是一个字符串，例如: org....RedisAutoConfiguration,org....RabbitAutoConfiguration，在这里我们扫描到的类装入了
     *  {@see excludeContainer} 中，所以需要进行处理成一个字符串，使用 {@see JOIN_COMMAND} 进行连接
     */
    public static final String JOIN_COMMAND = ",";

    /** 在 {@see GlobalAutoConfigurationSwitch#profiles} 文件中所有被排除的类（val=true），将会进入到此容器中 **/
    private static final List<String> excludeContainer = new ArrayList<>();

    /** 读取配置文件到本类中 **/
    private final String[] profiles = { "autoExcludeConfig.yml" };

    /** 将 properties 文件转换为此对象，方便存取 **/
    private final Properties properties = new Properties();

    /**
     * 由 {@see org.springframework.boot.env.EnvironmentPostProcessor} 提供的方法，
     * 主要目的是完成初始化阶段，读取配置文件spring.factories 的方法，并且在此方法内，可
     * 以自定义逻辑
     * @param environment   装配 spring.factories 的配置环境变量
     * @param application   springboot 应用程序配置信息
     */
    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        for (String profile : this.profiles) {
            // 读取配置文件为 Resource 对象
            Resource resource = new ClassPathResource(profile);
            // 加载并且解析出配置文件里面的信息
            loadAndParseProfiles(resource);

            if (CollectionUtils.isEmpty(excludeContainer)) { continue; }

            // 强行为当前 Properties 设置属性 spring.autoconfigure.exclude
            properties.setProperty(PROPERTY_NAME_AUTOCONFIGURE_EXCLUDE, arrayJoinSeparator());
            // 将该属性添加到全局环境变量中
            environment.getPropertySources().addLast(new PropertiesPropertySource(Objects.requireNonNull(resource.getFilename()), properties));
        }
    }

    /**
     * 加载，并且解析配置文件，如果又扫描到的类配置为 false，那么就将
     * 被扫描到的类装入容器 {@see excludeContainer} 中如果没有，那
     * 么直接跳过处理
     *
     * @param resource  被读取的配置文件所指向的 Resource 类
     */
    private void loadAndParseProfiles(Resource resource) {
        if (!resource.exists()) {
            throw new IllegalArgumentException("File " + resource + " not exist.");
        }

        String key = null;

        try {
            // 加载配置文件
            this.properties.load(resource.getInputStream());

            // 循环所有的配置项
            Set<String> set = this.properties.stringPropertyNames();
            if (CollectionUtils.isEmpty(set)) {
                return;
            }

            for (String a : set) {
                // cm.module.enable.redis
                key = a;
                // true/false
                String property = properties.getProperty(a);
                boolean bool = Boolean.parseBoolean(property);

                // true: 开启目标自动配置
                if (!bool) {
                    continue;
                }

                // 关闭目标自动配置
                // cm.module.enable.redis ->  ["cm", "module", "enable", "redis"]
                String[] split = key.split(REGEX_COMMAND);
                int len = split.length - 1;

                // ["cm", "module", "enable", "redis"] -> redis
                String containerKey = split[len];
                // 拿到被定义出来的目标自动配置类
                String clazzName = definitionExcludeContainers.get(containerKey);
                excludeContainer.add(clazzName);

            }
        } catch (Exception ex) {

            if (StringUtils.isNotBlank(key)) {
                throw new ClassCastException(resource.getFilename() + " -> " + key + "can not cast bool.");
            }

            throw new IllegalArgumentException("File " + resource.getFilename() + " load failure.");
        }
    }


    /**
     * 集合切分重新组合为字符串
     *
     * @return target not empty: ["1", "2"]  -->  1,2  /  target empty : ""
     */
    private static String arrayJoinSeparator() {

        int singleSize = 1, firstIndex = 0; String defaultStr = "";

        if (CollectionUtils.isEmpty(GlobalAutoConfigurationSwitch.excludeContainer)) {
            return defaultStr;
        }
        if (GlobalAutoConfigurationSwitch.excludeContainer.size() == singleSize) {
            return GlobalAutoConfigurationSwitch.excludeContainer.get(firstIndex);
        }

        StringBuilder builder = new StringBuilder();
        for (String str : GlobalAutoConfigurationSwitch.excludeContainer) {
            builder.append(GlobalAutoConfigurationSwitch.JOIN_COMMAND).append(str);
        }
        // before : ,"1","2"
        // after  :  "1","2"
        String excludes = builder.toString().substring(singleSize);

        if (log.isDebugEnabled()) {
            log.debug("Excluded components with: {}", excludes);
        }

        return excludes;
    }
}
