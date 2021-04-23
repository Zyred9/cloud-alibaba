package com.example.oauth.system.config.middleware.redis;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleCacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Objects;

/**
 * <p>
 *      redis 自动配置, 配合 com.example.oauth.system.config.exclude 包下的功能使用
 *      所以这里需要根据条件进行加载，只有当 RedisAutoConfiguration 存在 IOC 容器中，才
 *      加载该类
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@EnableCaching
@Configuration
@ConditionalOnBean(RedisAutoConfiguration.class)
public class RedisConfig extends CachingConfigurerSupport {

    @Autowired private LettuceConnectionFactory factory;


    public RedisConfig() {
        super();
    }

    @Override
    public CacheManager cacheManager() {
        RedisCacheWriter writer = RedisCacheWriter.lockingRedisCacheWriter(factory);
        //创建默认缓存配置对象
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig();
        return new RedisCacheManager(writer,config);
    }

    @Override
    public CacheResolver cacheResolver() {
        return super.cacheResolver();
    }

    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(method.getClass().getName());
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new SimpleCacheErrorHandler();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate () {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // general: k v
        template.setValueSerializer(new StringRedisSerializer());
        template.setKeySerializer(new StringRedisSerializer());

        FastJsonRedisSerializer<Object> fjs = new FastJsonRedisSerializer<>(Object.class);

        FastJsonConfig fjc = new FastJsonConfig();
        fjc.setSerializerFeatures(
                // 引用字段名称
                SerializerFeature.QuoteFieldNames,
                // 写入映射空值
                SerializerFeature.WriteMapNullValue,
                // 禁用循环参考检测
                SerializerFeature.DisableCircularReferenceDetect,
                // 写入日期使用日期格式
                SerializerFeature.WriteDateUseDateFormat,
                // 将空字符串写入为 ""
                SerializerFeature.WriteNullStringAsEmpty
        );

        fjs.setFastJsonConfig(fjc);

        // hash: k v
        template.setHashValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(fjs);

        template.setConnectionFactory(this.factory);
        return template;
    }

}
