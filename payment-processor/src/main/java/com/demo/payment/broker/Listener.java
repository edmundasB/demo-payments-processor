package com.demo.payment.broker;

import com.demo.payment.service.PaymentProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Listener {
	Logger logger = LoggerFactory.getLogger(Listener.class);
	private final PaymentProcessor processor;

	public Listener(PaymentProcessor processor) {
		this.processor = processor;
	}

	public void anotherServiceNotified(String paymentId) {
		logger.info("Another service processed payment id: " + paymentId);
		processor.checkNotified(paymentId);
	}
}

