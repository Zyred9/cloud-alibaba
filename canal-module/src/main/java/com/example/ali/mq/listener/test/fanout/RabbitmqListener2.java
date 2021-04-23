package com.example.ali.mq.listener.test.fanout;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Configuration;

/**
 * <p>
 *      Rabbitmq监听器
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
//@Configuration
@RabbitListener(queues = "work_queue2")
public class RabbitmqListener2 {

    @RabbitHandler
    public void readMessage (String msg) {
        System.out.println("work_queue2 :" + msg);
    }

}
