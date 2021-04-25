package com.example.oauth.system.el.event;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

/**
 * <p>
 *        创建事件描述，加入到 spring ioc 容器中
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Setter
@Getter
public class MyApplicationEvent extends ApplicationEvent {

    private static final long serialVersionUID = 1974388159657619599L;

    private String eventName;

    public MyApplicationEvent(Object source, String eventName) {
        super(source);
        this.eventName = eventName;
    }
}
