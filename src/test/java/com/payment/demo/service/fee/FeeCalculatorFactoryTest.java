package com.payment.demo.service.fee;

import com.payment.demo.model.PaymentType;
import com.payment.demo.service.fee.FeeCalculatorFactory;
import com.payment.demo.service.fee.Type1FeeCalculator;
import com.payment.demo.service.fee.Type2FeeCalculator;
import com.payment.demo.service.fee.Type3FeeCalculator;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class FeeCalculatorFactoryTest {
    @InjectMocks
    private FeeCalculatorFactory factory;
    @Mock
    public Type1FeeCalculator type1FeeCalculator;
    @Mock
    public Type2FeeCalculator type2FeeCalculator;
    @Mock
    public Type3FeeCalculator type3FeeCalculator;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getType1FeeCalculator() throws Exception {
        Assert.assertTrue(factory.getFeeCalculator(PaymentType.TYPE1) instanceof Type1FeeCalculator);
    }

    @Test
    public void getType2FeeCalculator() throws Exception {
        Assert.assertTrue(factory.getFeeCalculator(PaymentType.TYPE2) instanceof Type2FeeCalculator);
    }

    @Test
    public void getType3FeeCalculator() throws Exception {
        Assert.assertTrue(factory.getFeeCalculator(PaymentType.TYPE3) instanceof Type3FeeCalculator);
    }

}
