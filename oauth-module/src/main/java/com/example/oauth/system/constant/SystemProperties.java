package com.example.oauth.system.constant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *          系统常量，请勿更改
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix =  "cm.global")
public class SystemProperties {

    /** 全局消息序列化器 **/
    private String converter;

    /** 全全局时间处理格式，默认 yyyy-MM-dd HH:mm:ss，可选 yyyy-MM-dd **/
    private String formatter;

}
