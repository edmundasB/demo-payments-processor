package com.demo.payment.service.fee;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FeeCalculator {
     BigDecimal calculateCancellationFee(LocalDateTime paymentDate);
}
