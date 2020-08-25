package com.demo.payment.service;

import com.demo.payment.broker.Publisher;
import com.demo.payment.exception.PaymentException;
import com.demo.payment.model.PaymentDto;
import com.demo.payment.model.PaymentEntity;
import com.demo.payment.model.PaymentType;
import com.demo.payment.repository.PaymentRepository;
import com.demo.payment.service.fee.FeeCalculatorFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentProcessorImpl implements PaymentProcessor {
    private final PaymentRepository repository;
    private final FeeCalculatorFactory feeCalculator;
    private final Publisher publisher;

    public PaymentProcessorImpl(PaymentRepository repository, FeeCalculatorFactory feeCalculator, Publisher publisher) {
        this.repository = repository;
        this.feeCalculator = feeCalculator;
        this.publisher = publisher;
    }

    public void proceed(PaymentDto paymentDto) {
        PaymentEntity result = repository.save(new PaymentEntity(paymentDto));
        publisher.paymentCreated(result.getId());
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

    public List<PaymentEntity> find(BigDecimal amountFrom, BigDecimal amountTo, String status) {
        if(amountFrom != null && amountTo != null){
            return repository.findByStatusAndAmountIsGreaterThanAndAmountIsLessThan(status, amountFrom, amountTo);
        } else if (amountFrom != null){
            return repository.findByStatusAndAmountIsGreaterThan(status, amountFrom);
        } else if (amountTo != null){
            return repository.findByStatusAndAmountIsLessThan(status, amountTo);
        } else {
            return repository.findByStatus(status);
        }
    }

    public Optional<PaymentEntity> find(String paymentId, String status) {
        return repository.findByIdAndStatus(paymentId, status);
    }

    @Override
    public void checkNotified(String paymentId) {
        Optional<PaymentEntity> paymentEntity = repository.findById(paymentId);
        if(paymentEntity.isPresent()){
            PaymentEntity payment = paymentEntity.get();
            payment.setNotified();
            repository.save(payment);
        }
    }
}
