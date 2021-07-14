package com.example.mq.rabbit.direct;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Component
public class DirectModelListener {

    @Autowired private RabbitTemplate rabbitTemplate;

    @RabbitListener(queuesToDeclare = @Queue("direct_model"))
    public void receive1(String message){
        System.out.println("message1 = " + message);
    }

    @RabbitListener(queuesToDeclare = @Queue("direct_model"))
    public void receive2(String message){
        System.out.println("message2 = " + message);
    }



}
