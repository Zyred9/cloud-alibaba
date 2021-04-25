package com.example.oauth.system.el.listener;

import com.example.oauth.system.el.event.MyApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * <p>
 *      自定义事件监听
 * </p>
 *
 * @author zyred
 * @since v 0.1
 **/
@Component
public class MyApplicationListener {

    @Async
    @EventListener(MyApplicationEvent.class)
    public void onEvent(MyApplicationEvent event) {
        System.out.println("异步接收到事件["+Thread.currentThread().getName()+"]：" + event.getEventName());
    }
}
