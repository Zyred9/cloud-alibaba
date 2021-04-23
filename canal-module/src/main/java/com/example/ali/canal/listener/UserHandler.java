package com.example.ali.canal.listener;

import com.example.ali.common.entiy.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.javatool.canal.client.annotation.CanalTable;
import top.javatool.canal.client.handler.EntryHandler;


@Component
@CanalTable(value = "t_user")
public class UserHandler implements EntryHandler<User> {

    @Autowired private RabbitTemplate rabbitTemplate;

    private static final String exchange = "canal_topic";

    @Override
    public void insert(User user) {
        String routingKey = "user.insert";
        this.rabbitTemplate.convertAndSend(exchange, routingKey, user);
    }

    @Override
    public void update(User before, User after) {
        String routingKey = "user.update";
        this.rabbitTemplate.convertAndSend(exchange, routingKey, after);
    }

    @Override
    public void delete(User user) {
        String routingKey = "user.delete";
        this.rabbitTemplate.convertAndSend(exchange, routingKey, user);
    }

}