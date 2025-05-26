package com.example.demo.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.NewUser;
import com.example.demo.model.business.CallBusiness;

@Component
public class NewCallEventListener {
    
    private final CallBusiness callBusiness;

    public NewCallEventListener(CallBusiness callBusiness) {
        this.callBusiness = callBusiness;
    }
}
