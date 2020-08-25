package com.demo.payment.repository;

import com.demo.payment.model.PaymentEntity;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends CrudRepository<PaymentEntity, String> {
    List<PaymentEntity> findByStatusAndAmountIsGreaterThanAndAmountIsLessThan(String status, BigDecimal amountFrom, BigDecimal amountTo);
    List<PaymentEntity> findByStatusAndAmountIsGreaterThan(String status, BigDecimal amount);
    List<PaymentEntity> findByStatusAndAmountIsLessThan(String status, BigDecimal amount);
    List<PaymentEntity> findByStatus(String status);
    Optional<PaymentEntity> findByIdAndStatus(String id, String status);
}
