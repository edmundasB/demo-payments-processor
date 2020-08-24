package com.payment.demo.service.fee;

import com.payment.demo.service.fee.Type3FeeCalculator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class Type3FeeCalculatorTest {
    @InjectMocks
    @Spy
    private Type3FeeCalculator calculator;

    private LocalDateTime currentDateTime = LocalDateTime.of(2020, 8, 22, 12, 28);

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.when(calculator.getCurrentDateTime()).thenReturn(currentDateTime);
    }

        @Test
    public void cancelPayment() {
        // Fee of 3 hours, (3 * 0.15 )
        Assert.assertEquals( BigDecimal.valueOf(0.45), calculator.calculateCancellationFee(currentDateTime.minusHours(3)));
    }

}
