package com.example.ali.canal;

import com.example.ali.CanalApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *          MQ
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CanalApplication.class)
public class CanalQueueApplicationTest {

    @Autowired private RabbitTemplate rabbitTemplate;

    @Test
    public void sendQueue () throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            this.rabbitTemplate.convertAndSend("zyred_work", "第" +i+"条消息");
        }

        Thread.sleep(5000);
    }


    // 广播的形式发送，同一个路由下的所有队列都能接收到消息
    @Test
    public void sendFanout() throws InterruptedException {
        System.out.println("开始向路由发送消息（路由下的所有Queue都能收到该消息）");
        // 参数1：路由名  参数2：可有可无    参数3：发送的消息内容
        rabbitTemplate.convertAndSend("zyred_work","","这是一条所有消费者都能收到的消息！");
        System.out.println("消息发送成功！");
        Thread.sleep(5000);
    }

    @Test
    public void sendTopic1() throws InterruptedException {
        System.out.println("开始向路由中发送消息！参数2：routingKey");
        // 参数1：路由器名  参数2：类似于发送的规则名
        rabbitTemplate.convertAndSend("zyred_topic1","good.log","这是一条good.log消息");
        System.out.println("消息发送成功！");
        Thread.sleep(5000);
    }

    @Test
    public void sendTopic2() throws InterruptedException {
        System.out.println("开始向路由中发送消息！参数2：routingKey");
        rabbitTemplate.convertAndSend("zyred_topic1","维护.log","这是一条 维护.log消息");
        rabbitTemplate.convertAndSend("zyred_topic1","日志.log","这是一条 日志.log消息");
        System.out.println("消息发送成功！");
        Thread.sleep(5000);
    }

    @Test
    public void sendTopic3(){
        // 1.准备发送的数据
        Map map = new HashMap();
        map.put(1,"a");
        map.put(2,"b");
        List list = new ArrayList();
        list.add("qq");
        list.add("ww");
        list.add("SS");
        System.out.println("开始向路由中发送消息！参数2为routingKey");
        // 2.开始发送消息（发送了2条）
        // 2.1 发送的数据为map类型
        rabbitTemplate.convertAndSend("zyred_topic1","good.txt",map);
        // 2.2 发送的数据为list类型
        rabbitTemplate.convertAndSend("zyred_topic1","good.txt",list);
    }
}
