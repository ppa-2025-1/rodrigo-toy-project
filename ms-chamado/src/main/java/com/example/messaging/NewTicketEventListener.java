package com.example.messaging;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.example.config.RabbitMQConfig;
import com.example.model.business.TicketBusiness;

@Component
public class NewTicketEventListener {
    
    private final TicketBusiness ticketBusiness;

    public NewTicketEventListener(TicketBusiness ticketBusiness) {
        this.ticketBusiness = ticketBusiness;
    }
}
