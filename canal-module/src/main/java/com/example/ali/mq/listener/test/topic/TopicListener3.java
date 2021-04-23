package com.example.ali.mq.listener.test.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
//@Configuration
@RabbitListener(queues = "topic_queue3")
public class TopicListener3 {

    @RabbitHandler
    public void getMessage (String msg) {
        System.out.println("topic_queue3 :" + msg);
    }

    /**
     * 可以进行重载，会找到指定参数的queue上
     * @param map
     */
    @RabbitHandler
    public void getMessage(Map map){
        System.out.println("topic_queue3 收到的（map）消息如下：" + map);
    }
    @RabbitHandler
    public void getMessage(List list){
        System.out.println("topic_queue3 收到的（list）消息如下：" + list);
    }
}
