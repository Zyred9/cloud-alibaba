package com.example.mq.rabbit.direct;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
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
public class FanoutListener {

    @Autowired private RabbitTemplate rabbitTemplate;

    @RabbitListener(
        bindings = @QueueBinding(
                value = @Queue,
                exchange = @Exchange(name = "fanout_exchange", type = ExchangeTypes.FANOUT)
        )
    )
    public void consumer1 (String msg) {
        System.out.println("消费者1：" + msg);
    }


    @RabbitListener(
        bindings = @QueueBinding(
                value = @Queue,
                exchange = @Exchange(name = "fanout_exchange", type = ExchangeTypes.FANOUT)
        )
    )
    public void consumer2 (String msg) {
        System.out.println("消费者2：" + msg);
    }

}
