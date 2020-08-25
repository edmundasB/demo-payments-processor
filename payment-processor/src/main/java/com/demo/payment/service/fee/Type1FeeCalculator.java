package com.demo.payment.service.fee;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class Type1FeeCalculator implements FeeCalculator {
    @Override
    public BigDecimal calculateCancellationFee(LocalDateTime paymentDate) {
        long diff = paymentDate.until( getCurrentDateTime(), ChronoUnit.HOURS );
        return BigDecimal.valueOf(0.05).multiply(BigDecimal.valueOf(diff));
    }

    public LocalDateTime getCurrentDateTime(){
        return LocalDateTime.now();
    }
}
