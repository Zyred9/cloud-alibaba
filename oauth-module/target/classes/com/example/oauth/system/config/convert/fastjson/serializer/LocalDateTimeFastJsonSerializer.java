package com.example.oauth.system.config.convert.fastjson.serializer;

import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.example.oauth.comm.utils.TimeUtils;
import com.example.oauth.system.constant.SystemConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * <p>
 *          FastJson 针对 LocalDateTime 序列化一直有问题，因为 LocalDateTime
 *          默认的格式为 yyyy-MM-ddTHH:mm:sss，但是在实际开发中，为了更加直观的
 *          查看，必须要满足 yyyy-MM-dd HH:mm:ss 作为默认的格式。再此，将通过全
 *          局统一默认格式序列化 LocalDateTime 类型的数据
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Slf4j
public class LocalDateTimeFastJsonSerializer implements ObjectSerializer {

    private static String       DEFAULT_FORMATTER              =    TimeUtils.Y_M_D_H_M_S;

    /**
     * 通过构造器，将 spring boot 环境加载到此类中
     *
     * @param env  从环境中读取 {@code YML_GLOBAL_FORMATTER_KEY}
     *             变量，来分析是否被自定义过全局时间类型
     */
    public LocalDateTimeFastJsonSerializer(Environment env) {
        // 读取配置文件，这是默认的全局时间格式
        String property = env.getProperty(SystemConstant.YML_GLOBAL_FORMATTER_KEY);
        if (StringUtils.isNotBlank(property)) {
            this.dateFormatterValid(property);
            DEFAULT_FORMATTER = property;
            log.info("FastJson serializer LocalDateTime formatter with {}", DEFAULT_FORMATTER);
        }
    }

    private void dateFormatterValid (String property) {
        String[] formatter = TimeUtils.getDefinitionFormatter();
        for (String s : formatter) {
            if (s.equals(property)) {
                return;
            }
        }
        throw new IllegalArgumentException("Properties format error with "
                + SystemConstant.YML_GLOBAL_FORMATTER_KEY + " value " + property
                + ", please add this value to " + TimeUtils.class.getName());
    }

    @Override
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) {
        SerializeWriter writer = serializer.out;

        if (Objects.isNull(object)) {
            writer.writeNull();
            return;
        }

        LocalDateTime localDateTime = (LocalDateTime) object;
        String format = localDateTime.format(DateTimeFormatter.ofPattern(DEFAULT_FORMATTER));

        writer.writeString(format);
    }
}
