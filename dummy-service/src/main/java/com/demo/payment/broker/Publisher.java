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

    public void confirmReceived(String paymentId) {
        logger.info("DUMMY SERVICE. Inform other services about processed payment id " + paymentId);
        rabbitTemplate.convertAndSend("payment-confirmed", "com.payment.demo",  paymentId);
    }

}
