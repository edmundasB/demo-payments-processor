package com.payment.demo.validation;

import com.payment.demo.model.PaymentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PaymentTypeValidator implements ConstraintValidator<PaymentTypeValidate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            PaymentType.valueOf(value);
        return true;
        } catch (Exception e){
            return false;
        }
    }
}


