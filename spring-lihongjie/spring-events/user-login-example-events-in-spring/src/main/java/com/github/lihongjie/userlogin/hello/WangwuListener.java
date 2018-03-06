package com.github.lihongjie.userlogin.hello;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class WangwuListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(final Class<? extends ApplicationEvent> eventType) {
        return eventType == ContentEvent.class;
    }
    
    @Override
    public boolean supportsSourceType(final Class<?> sourceType) {
        return sourceType == String.class;
    }
    
    @Override
    public void onApplicationEvent(final ApplicationEvent event) {
        System.out.println("王五在孙六之前收到新的内容：" + event.getSource());
    }
    
    @Override
    public int getOrder() {
        return 1;
    }
}
