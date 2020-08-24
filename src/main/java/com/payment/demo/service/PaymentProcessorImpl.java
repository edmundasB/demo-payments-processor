package com.payment.demo.service;

import com.payment.demo.exception.PaymentException;
import com.payment.demo.model.PaymentDto;
import com.payment.demo.model.PaymentEntity;
import com.payment.demo.model.PaymentType;
import com.payment.demo.repository.PaymentRepository;
import com.payment.demo.service.fee.FeeCalculatorFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentProcessorImpl implements PaymentProcessor {
    private final PaymentRepository repository;
    private final FeeCalculatorFactory feeCalculator;

    public PaymentProcessorImpl(PaymentRepository repository, FeeCalculatorFactory feeCalculator) {
        this.repository = repository;
        this.feeCalculator = feeCalculator;
    }

    public void proceed(PaymentDto paymentDto) {
        repository.save(new PaymentEntity(paymentDto));
    }

    public void cancel(String paymentId) throws Exception {
        Optional<PaymentEntity> paymentEntity = repository.findById(paymentId);
        if(paymentEntity.isPresent()){
            PaymentEntity payment = paymentEntity.get();
            isSameDay(payment.getCreateDate());

            BigDecimal fee = feeCalculator.getFeeCalculator(PaymentType.valueOf(payment.getType()))
                    .calculateCancellationFee(payment.getCreateDate());

            payment.setFee( fee);

            payment.cancel();
            repository.save(payment);
        } else {
            throw new PaymentException("Payment " + paymentId + " not found.");
        }
    }

    private void isSameDay(LocalDateTime paymentDate) throws PaymentException {
        if(paymentDate.getDayOfWeek().compareTo(getCurrentDateTime().getDayOfWeek()) != 0){
            throw new PaymentException("Payment cannot be cancelled");
        }
    }

    public LocalDateTime getCurrentDateTime(){
        return LocalDateTime.now();
    }

    public List<PaymentEntity> find(BigDecimal amountFrom, BigDecimal amountTo) {
        if(amountFrom != null && amountTo != null){
            return  repository.findByAmountIsGreaterThanAndAmountIsLessThan(amountFrom, amountTo);
        } else if (amountFrom != null){
            return   repository.findByAmountIsGreaterThan(amountFrom);
        } else if (amountTo != null){
            return repository.findByAmountIsLessThan(amountTo);
        } else {
            return (List<PaymentEntity>) repository.findAll();
        }
    }

    public Optional<PaymentEntity> findById(String paymentId){
        return repository.findById(paymentId);
    }
}
