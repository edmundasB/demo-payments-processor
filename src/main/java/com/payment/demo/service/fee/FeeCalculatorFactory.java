package com.payment.demo.service.fee;

import com.payment.demo.model.PaymentType;
import org.springframework.stereotype.Component;

@Component
public class FeeCalculatorFactory {
    public final Type1FeeCalculator t1Calculator;
    public final Type2FeeCalculator t2Calculator;
    public final Type3FeeCalculator t3Calculator;

    public FeeCalculatorFactory(Type1FeeCalculator t1Calculator, Type2FeeCalculator t2Calculator, Type3FeeCalculator t3Calculator) {
        this.t1Calculator = t1Calculator;
        this.t2Calculator = t2Calculator;
        this.t3Calculator = t3Calculator;
    }

    public FeeCalculator getFeeCalculator(PaymentType type) throws Exception {
        switch (type) {
            case TYPE1:
                return t1Calculator;
            case TYPE2:
                return t2Calculator;
            case TYPE3:
                return t3Calculator;
        }

        throw new Exception("FeeCalculator not supported. ");
    }
}
