package com.example.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.config.RabbitMQConfig;
import com.example.dto.NewUser;
import com.example.model.business.UserBusiness;

@Component
public class NewUserEventListener {

    private final UserBusiness userBusiness;

    public NewUserEventListener(UserBusiness userBusiness) {
        this.userBusiness = userBusiness;
    }


    @RabbitListener(queues = RabbitMQConfig.USER_QUEUE)
    public void newUser(NewUser event) {
        userBusiness.criarUsuario(event);
    }
}