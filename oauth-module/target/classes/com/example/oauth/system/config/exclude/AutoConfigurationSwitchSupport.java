package com.example.oauth.system.config.exclude;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 *          本类中，主要是在开发阶段完成类型的定义，例如 redis -> org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration
 *          起到一个通过别名，获取到对应组件的自动装配对象
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
public class AutoConfigurationSwitchSupport {

    /**
     * 开发阶段，定义好的所有 {@see key} 自定义类型和 {@see val} 类型对应的自动配置类全路径名称
     *  key: 自定义类型   val: 组件在spring boot 中自动配置的类全路径名称
     */
    protected static final Map<String, String> definitionExcludeContainers = new ConcurrentHashMap<>();

    static {
        // key: 自定义类型   val: 组件在spring boot 中自动配置的类全路径名称
        // redis：移除redis，将会失去redis的功能，如果此时也关闭了 cache，那么 redis 也将失去作用
        // cm.module.enable.redis=false
        definitionExcludeContainers.put("redis", "org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration");
        // RabbitMQ: 移除 RabbitMQ的自动注入，将会失去 RabbitMQ 的功能
        // cm.module.enable.rabbitMq=false
        definitionExcludeContainers.put("rabbitMq", "org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration");
        // 关闭该包后，spring boot 自动配置的缓存将会失效，redis 自动配置将不会被自动注入
        // cm.module.enable.cache=false
        definitionExcludeContainers.put("cache", "org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration");
        // 关闭该包后，spring boot 自动配置的缓存将会失效，elasticSearch 自动配置将不会被自动注入
        // cm.module.enable.elasticSearch=false
        definitionExcludeContainers.put("elasticSearch", "org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 JPA 作为 持久层框架
        // cm.module.enable.jpa=false
        definitionExcludeContainers.put("jpa", "org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 mongoDB
        // cm.module.enable.mongoDB=false
        definitionExcludeContainers.put("mongoDB", "org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 mongo 自动配置
        // cm.module.enable.mongo=false
        definitionExcludeContainers.put("mongo", "org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 freeMarker
        // cm.module.enable.freeMarker=false
        definitionExcludeContainers.put("freeMarker", "org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 groovyTemplate
        // cm.module.enable.groovyTemplate=false
        definitionExcludeContainers.put("groovyTemplate", "org.springframework.boot.autoconfigure.groovy.template.GroovyTemplateAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 Gson JSON 序列化器
        // cm.module.enable.gson=false
        definitionExcludeContainers.put("gson", "org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 h2 数据库
        // cm.module.enable.gson=false
        definitionExcludeContainers.put("h2", "org.springframework.boot.autoconfigure.h2.H2ConsoleAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 jackson json 序列化器
        // cm.module.enable.httpMessageConvert=false
        definitionExcludeContainers.put("jackson", "org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 jdbcDataSource
        // cm.module.enable.jdbcDataSource=false
        definitionExcludeContainers.put("jdbcDataSource", "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 jdbcTemplate
        // cm.module.enable.jdbcTemplate=false
        definitionExcludeContainers.put("jdbcTemplate", "org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 jdbc 事务管理器
        // cm.module.enable.dataSourceTransactionManager=false
        definitionExcludeContainers.put("dataSourceTransactionManager", "org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 activeMq
        // cm.module.enable.activeMq=false
        definitionExcludeContainers.put("activeMq", "org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 kafka
        // cm.module.enable.kafka=false
        definitionExcludeContainers.put("kafka", "org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 quartz 定时框架
        // cm.module.enable.quartz=false
        definitionExcludeContainers.put("quartz", "org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 oauth2
        // cm.module.enable.oauth2=false
        definitionExcludeContainers.put("oauth2", "org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 druid 数据库连接池
        // cm.module.enable.druid=false
        definitionExcludeContainers.put("druid", "com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure");
        // 关闭该包后，spring boot 项目不将使用 mybatisPlus
        // cm.module.enable.mybatisPlus=false
        definitionExcludeContainers.put("mybatisPlus", "com.baomidou.mybatisplus.autoconfigure.MybatisPlusAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 easyPoi
        // cm.module.enable.easyPoi=false
        definitionExcludeContainers.put("easyPoi", "cn.afterturn.easypoi.configuration.EasyPoiAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 activitiDataSource 作为 activiti 数据库连接配置
        // cm.module.enable.activitiDataSource=false
        definitionExcludeContainers.put("activitiDataSource", "org.activiti.spring.boot.SecurityAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 h2 作为 oauth2 的默认配置
        // cm.module.enable.oauth2H2=false
        definitionExcludeContainers.put("oauth2H2", "org.springframework.boot.autoconfigure.security.oauth2.OAuth2AutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 session 自动配置
        // cm.module.enable.session=false
        definitionExcludeContainers.put("session", "org.springframework.boot.autoconfigure.session.SessionAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 webSocket 自动配置
        // cm.module.enable.webSocket=false
        definitionExcludeContainers.put("webSocket", "org.springframework.boot.autoconfigure.websocket.servlet.WebSocketMessagingAutoConfiguration");
        // 关闭该包后，spring boot 项目不将使用 aop 自动配置
        // cm.module.enable.aop=false
        definitionExcludeContainers.put("aop", "org.springframework.boot.autoconfigure.aop.AopAutoConfiguration");
    }

}