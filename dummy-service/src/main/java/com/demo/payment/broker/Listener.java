package com.demo.payment.broker;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class Listener {
	Logger logger = LoggerFactory.getLogger(Listener.class);
	private final Publisher publisher;

	public Listener(Publisher publisher) {
		this.publisher = publisher;
	}

	public void paymentCreatedMsg(String paymentId) {
		logger.info("DUMMY SERVICE. Received payment id " + paymentId);
		publisher.confirmReceived(paymentId);
	}
}

