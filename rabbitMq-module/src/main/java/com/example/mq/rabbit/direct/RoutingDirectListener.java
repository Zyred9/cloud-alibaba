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
public class RoutingDirectListener {

    @Autowired private RabbitTemplate rabbitTemplate;

    @RabbitListener(
        bindings = @QueueBinding(
                key = {"error", "info"},
                value = @Queue,
                exchange = @Exchange(name = "routing_direct_rest", type = ExchangeTypes.DIRECT)
        )
    )
    public void consumer1 (String msg) {
        System.out.println("消费者1：" + msg);
    }


    @RabbitListener(
        bindings = @QueueBinding(
                key = "error",
                value = @Queue,
                exchange = @Exchange(name = "routing_direct_rest", type = ExchangeTypes.DIRECT)
        )
    )
    public void consumer2 (String msg) {
        System.out.println("消费者2：" + msg);
    }

}
