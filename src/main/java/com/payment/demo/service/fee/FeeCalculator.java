package com.payment.demo.service.fee;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface FeeCalculator {
     BigDecimal calculateCancellationFee(LocalDateTime paymentDate);
}
