package com.demo.payment.service;

import com.demo.payment.model.PaymentDto;
import com.demo.payment.model.PaymentEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentProcessor {
    void proceed(PaymentDto paymentDto);
    void cancel(String paymentId) throws Exception;
    List<PaymentEntity> find(BigDecimal amountFrom, BigDecimal amountTo, String status);
    Optional<PaymentEntity> find(String paymentId, String status);
    void checkNotified(String paymentId);
}
