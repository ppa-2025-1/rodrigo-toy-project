package com.example.demo.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.demo.config.RabbitMQConfig;
import com.example.demo.dto.NewUser;
import com.example.demo.model.business.TicketBusiness;

@Component
public class NewTicketEventListener {
    
    private final TicketBusiness ticketBusiness;

    public NewTicketEventListener(TicketBusiness ticketBusiness) {
        this.ticketBusiness = ticketBusiness;
    }
}
