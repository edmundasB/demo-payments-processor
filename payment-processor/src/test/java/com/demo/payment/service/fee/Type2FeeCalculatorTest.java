package com.demo.payment.service.fee;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDateTime;

class Type2FeeCalculatorTest {
    @InjectMocks
    @Spy
    private Type2FeeCalculator calculator;

    private LocalDateTime currentDateTime = LocalDateTime.of(2020, 8, 22, 12, 28);

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        Mockito.when(calculator.getCurrentDateTime()).thenReturn(currentDateTime);
    }

        @Test
    public void cancelPayment() {
        // Fee of 3 hours, (3 * 0.3 )
        Assert.assertEquals( BigDecimal.valueOf(0.3), calculator.calculateCancellationFee(currentDateTime.minusHours(3)));
    }

}
