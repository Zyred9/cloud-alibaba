package com.example.ali.mq.listener.test.topic;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
//@Configuration
@RabbitListener(queues = "topic_queue2")
public class TopicListener2 {

    @RabbitHandler
    public void getMessage (String msg) {
        System.out.println("topic_queue2 :" + msg);
    }

}
