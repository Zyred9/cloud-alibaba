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
 *      * (star) can substitute for exactly one word.    匹配不多不少恰好1个词
 *      # (hash) can substitute for zero or more words.  匹配一个或多个词
 *
 *
 *      audit.#    匹配audit.irs.corporate或者 audit.irs 等
 *      audit.*   只能匹配 audit.irs
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Component
public class TopicListener {

    @RabbitListener(
        bindings = @QueueBinding(
                key = "user.*",
                value = @Queue,
                exchange = @Exchange(name = "topics_exchange", type = ExchangeTypes.TOPIC)
        )
    )
    public void consumer1 (String msg) {
        System.out.println("消费者1：" + msg);
    }


    @RabbitListener(
        bindings = @QueueBinding(
                key = "user.#",
                value = @Queue,
                exchange = @Exchange(name = "topics_exchange", type = ExchangeTypes.TOPIC)
        )
    )
    public void consumer2 (String msg) {
        System.out.println("消费者2：" + msg);
    }

}
