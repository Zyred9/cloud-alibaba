package com.example.ali.mq.listener.canal;

import com.alibaba.fastjson.JSON;
import com.example.ali.common.entiy.User;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * <p>
 *          监听MQ，如果canal监听到数据库变化，那么canal将会发送一条消息到MQ中
 *          由MQ发送消息
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Configuration
@RabbitListener(queues = "canal_queue")
public class CanalMqListener {

    @Autowired private RedisTemplate<String, String> redisTemplate;

    @RabbitHandler
    public void updateForRedis (User user) {
        String key = String.valueOf(user.getId());
        this.redisTemplate.opsForValue().set(key, JSON.toJSONString(user));
    }

}
