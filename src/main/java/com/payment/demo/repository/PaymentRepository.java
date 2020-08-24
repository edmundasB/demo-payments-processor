package com.payment.demo.repository;

import com.payment.demo.model.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentRepository extends CrudRepository<PaymentEntity, String> {
    List<PaymentEntity> findByAmountIsGreaterThanAndAmountIsLessThan(BigDecimal amountFrom, BigDecimal amountTo);
    List<PaymentEntity> findByAmountIsGreaterThan(BigDecimal amount);
    List<PaymentEntity> findByAmountIsLessThan(BigDecimal amount);
}
