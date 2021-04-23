package com.example.oauth.system.config.convert.jackson;

import com.example.oauth.comm.constant.SystemConstant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers;
import com.fasterxml.jackson.databind.ser.std.DateSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.SneakyThrows;
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
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * <p>
 * 全局json序列化器 jackson2
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Slf4j
@Configuration
@Conditional(GlobalJacksonHttpMessageConvertConfiguration.class)
public class GlobalJacksonHttpMessageConvertConfiguration extends WebMvcConfigurationSupport implements Condition {


    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 移除默认的消息转换器，替换为自定义的消息转换器
        converters.removeIf(converter -> converter instanceof MappingJackson2HttpMessageConverter);
        converters.add(getMappingJackson2HttpMessageConverter());
    }

    /**
     * 创建以 jackson2 为序列化和反序列的消息转换器
     *
     * @return      jackson2
     */
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter jack = new MappingJackson2HttpMessageConverter();

        jack.setObjectMapper(this.createProjectObjectMapper());

        //设置中文编码格式
        List<MediaType> list = new ArrayList<MediaType>();
        list.add(MediaType.APPLICATION_JSON);
        jack.setSupportedMediaTypes(list);

        return jack;
    }


    /**
     * 创建本项目中，结果集映射的关系配置
     *
     * @return om
     */
    private ObjectMapper createProjectObjectMapper() {

        //设置日期格式
        ObjectMapper om = new ObjectMapper();

        om.enable(
                // BigDecimal 普通模式
                JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN,
                // 严格的重复检测
                JsonGenerator.Feature.STRICT_DUPLICATE_DETECTION,
                // JSON 内容补全
                JsonGenerator.Feature.AUTO_CLOSE_TARGET
        );

        om.enable(
                // 使用 Java Array 来表示 JSON array
                DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY,
                // 位置属性时，反序列化无属性，则报错
                DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                // 急切的反序列化器获取， 提升性能
                DeserializationFeature.EAGER_DESERIALIZER_FETCH,
                // 根据上下文时区调整日期
                DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE
        );

        // 关闭
        om.disable(
                // 空数据转换为空对象
                DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT,
                // 接受 float 为 int 类型
                DeserializationFeature.ACCEPT_FLOAT_AS_INT
        );

        om.enable(
                // 缩进输出，
                SerializationFeature.INDENT_OUTPUT,
                // 失败，输出 null object
                SerializationFeature.FAIL_ON_EMPTY_BEANS,
                // 写值后进行刷新
                SerializationFeature.FLUSH_AFTER_WRITE_VALUE
        );

        // 关闭将日期写入时间戳
        om.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        om.registerModule(createTimeModule());

        nullValueSerializer(om.getSerializerProvider());

        return om;
    }


    /**
     * 创建时间处理的模型
     *
     * @return      时间处理模型，包含 LocalDate  LocalTime  LocalDateTime 的序列化和反序列化
     */
    public Module createTimeModule () {

        JavaTimeModule jtm = new JavaTimeModule();
        jtm.addSerializer(Date.class, new DateSerializer(false, new SimpleDateFormat(SystemConstant.DEFAULT_DATE_TIME_PATTERN)));
        jtm.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(SystemConstant.DEFAULT_DATE_PATTERN)));
        jtm.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(SystemConstant.DEFAULT_TIME_PATTERN)));
        jtm.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(SystemConstant.DEFAULT_DATE_TIME_PATTERN)));

        jtm.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(SystemConstant.DEFAULT_DATE_TIME_PATTERN)));
        jtm.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(SystemConstant.DEFAULT_DATE_PATTERN)));
        jtm.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(SystemConstant.DEFAULT_TIME_PATTERN)));
        jtm.addDeserializer(Date.class, new DateDeserializers.DateDeserializer());

        return jtm;
    }


    public void nullValueSerializer (SerializerProvider p) {
        if (Objects.isNull(p)) {
            return;
        }



        p.setNullValueSerializer(new JsonSerializer<Object>() {
            @Override
            public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                String targetName = gen.getOutputContext().getCurrentName();
                Object currentValue = gen.getCurrentValue();
                putTypeDefaultVal(gen, currentValue, targetName);
            }
        });
    }

    @SneakyThrows
    public void putTypeDefaultVal (JsonGenerator gen, Object currentValue, String targetName) {
        if (Objects.isNull(gen) || Objects.isNull(currentValue) || StringUtils.isBlank(targetName)) {
            return ;
        }

        Class<?> clazz = currentValue.getClass();
        Field field = clazz.getDeclaredField(targetName);

        writeTypeDefaultVal(field.getType(), gen);
    }


    @SneakyThrows
    public void writeTypeDefaultVal (Class<?> clazz, JsonGenerator gen) {
        if (Objects.isNull(clazz) || clazz == Map.class || clazz == Set.class) {
            gen.writeNull();
            return;
        }

        if (clazz == Integer.class || clazz == Long.class
                || clazz == Double.class || clazz == Float.class
                || clazz == Byte.class || clazz == Short.class) {
            gen.writeNumber(0);
            return;
        }
        if (clazz == Boolean.class) {
            gen.writeBoolean(false);
            return;
        }
        if (clazz == String.class) {
            gen.writeString("");
            return;
        }
        if (clazz == List.class) {
            gen.writeArray(new int[0], 0, 0);
            return;
        }
        gen.writeNull();
    }

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Environment environment = context.getEnvironment();
        String converter = environment.getProperty(com.example.oauth.system.constant.SystemConstant.CM_GLOBAL_CONVERTER);

        boolean equals = Objects.equals(com.example.oauth.system.constant.SystemConstant.CONVERTER_NAME_JACK_SON_2, converter);

        if (equals) {
            log.info("Setting global http message converter with {}. ", this.getClass().getName());
        }
        return equals;
    }
}
