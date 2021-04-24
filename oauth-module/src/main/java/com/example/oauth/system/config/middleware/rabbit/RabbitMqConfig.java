package com.example.oauth.system.config.middleware.rabbit;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;

/**
 * <p>
 *      RabbitMQ 核心配置文件, 只有当项目开启 rabbitMQ 核心自动配置文件后，
 *      该类才会进行 IOC 注入
 * </p>
 *
 * @author Zyred
 * @date 2021/4/24 10:59
 **/
@Configurable
@ConditionalOnBean(RabbitAutoConfiguration.class)
public class RabbitMqConfig {
}
