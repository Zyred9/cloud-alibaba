package com.example.mq;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p>
 *
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@SpringBootTest(classes = MqApplication.class)
public class DirectModelListenerTest {

    @Autowired private RabbitTemplate rabbitTemplate;

    @Test
    public void testDirect (){
        for (int i = 0; i < 10; i++) {
            this.rabbitTemplate.convertAndSend("direct_model", "直连模式发送 hello word！" + i);
        }
    }

    @Test
    public void testFanout() throws InterruptedException {
        rabbitTemplate.convertAndSend("fanout_exchange","","这是广播");
    }

    @Test
    public void testRoutingDirect () {
        this.rabbitTemplate.convertAndSend("routing_direct_rest", "info", "这是一条error消息");
    }


    @Test
    public void testTopic(){
        rabbitTemplate.convertAndSend("topics_exchange","user.save","user.save.findAll 的消息");
    }
}
