package com.payment.demo.service;

import com.payment.demo.model.PaymentDto;
import com.payment.demo.model.PaymentEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentProcessor {
    void proceed(PaymentDto paymentDto);
    void cancel(String paymentId) throws Exception;
    List<PaymentEntity> find(BigDecimal amountFrom, BigDecimal amountTo);
    Optional<PaymentEntity> findById(String paymentId);
}
