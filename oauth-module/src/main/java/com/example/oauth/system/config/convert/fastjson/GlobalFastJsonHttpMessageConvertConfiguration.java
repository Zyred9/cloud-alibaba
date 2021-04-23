package com.example.oauth.system.config.convert.fastjson;

import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.example.oauth.system.config.convert.fastjson.serializer.LocalDateTimeFastJsonSerializer;
import com.example.oauth.system.config.convert.fastjson.serializer.Oauth2AccessTokenFastJsonSerializer;
import com.example.oauth.system.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *          全局消息转换器配置，使用 FastJson 来处理消息
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Slf4j
@Configuration
@Conditional(GlobalFastJsonHttpMessageConvertConfiguration.class)
public class GlobalFastJsonHttpMessageConvertConfiguration implements WebMvcConfigurer, Condition {

    @Autowired private Environment environment;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
//        converters.removeIf(convert -> convert instanceof FastJsonHttpMessageConverter);
//        converters.removeIf(convert -> convert instanceof MappingJackson2HttpMessageConverter);
        converters.add(this.fastJsonHttpMessageConvert());
    }

    private HttpMessageConverter<?> fastJsonHttpMessageConvert() {
        FastJsonHttpMessageConverter fc = new FastJsonHttpMessageConverter();

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

        this.setSerializeConfig(fjc.getSerializeConfig());

        List<MediaType> mediaTypeList = new ArrayList<>();
        mediaTypeList.add(MediaType.APPLICATION_JSON);
        fc.setSupportedMediaTypes(mediaTypeList);
        fc.setFastJsonConfig(fjc);


        return fc;
    }


    private void setSerializeConfig (SerializeConfig config) {
        // 针对 OAuth2AccessToken 自定义的序列化器
        config.put(DefaultOAuth2AccessToken.class, Oauth2AccessTokenFastJsonSerializer.INSTANCE);
        // 针对 LocalDateTime 自定义的序列化器
        config.put(LocalDateTime.class, new LocalDateTimeFastJsonSerializer(environment));
    }


    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String converter = environment.getProperty(SystemConstant.CM_GLOBAL_CONVERTER);

        boolean equals = Objects.equals(SystemConstant.CONVERTER_NAME_FAST_JSON, converter);

        // 默认设置为 fastjson 作为消息转换器
        equals = StringUtils.isBlank(converter) || equals;


        if (equals) {
            log.info("Setting global http message converter with {}. ", this.getClass().getName());
        }
        return equals;
    }
}
