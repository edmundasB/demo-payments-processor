package com.demo.payment.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class Publisher {
    Logger logger = LoggerFactory.getLogger(Publisher.class);
    private final RabbitTemplate rabbitTemplate;

    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void paymentCreated(String paymentId) {
        logger.info("Other services has been notified about payment id: " + paymentId);
        rabbitTemplate.convertAndSend("payment-created", "com.payment.demo",  paymentId);
    }

}
