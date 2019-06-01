package com.example.hexagonalarch.cnfg;

import com.example.hexagonalarch.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;

@Configuration
public class JmsCnfg {

    @Autowired
    private JmsTemplate jmsTemplate;

    public void sendMessage() {
        User user = new User();
        user.setName("User1");
        user.setAge(18);
        jmsTemplate.convertAndSend("queue1", user);
    }
}
