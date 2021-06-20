package com.behsa.smsgw;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("{exchange}")
    private String exchange;

    @Value("{routingkey}")
    private String routingkey;

    public void send(String user) {
        rabbitTemplate.convertAndSend(exchange, routingkey, user);
        System.out.println("Send msg = " + user);

    }
}