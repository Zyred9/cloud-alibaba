package com.example.oauth.system.el.event;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 自定义事件发布器
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Component
public class MyEventPublisher {

    private final ApplicationEventPublisher publisher;

    MyEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    public void publishEvent(final String name) {
        publisher.publishEvent(new MyApplicationEvent(this, name));
    }

}
