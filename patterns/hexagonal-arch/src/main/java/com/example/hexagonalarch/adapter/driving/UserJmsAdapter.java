package com.example.hexagonalarch.adapter.driving;

import com.example.hexagonalarch.bo.UserBo;
import com.example.hexagonalarch.port.driving.CreateUserPort;
import com.example.hexagonalarch.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class UserJmsAdapter implements CreateUserPort {

    @Autowired
    private UserBo userBo;

    @JmsListener(destination = "queue1")
    public void receiveMsg(User user) {
        createUser(user);
    }

    @Override
    public boolean createUser(User user) {
        return userBo.createUser(user);
    }
}
