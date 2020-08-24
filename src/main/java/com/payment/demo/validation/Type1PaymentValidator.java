package com.payment.demo.validation;

import com.payment.demo.model.CurrencyType;
import com.payment.demo.model.PaymentDto;
import com.payment.demo.model.PaymentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Type1PaymentValidator implements ConstraintValidator<Type1PaymentValidate, PaymentDto> {
    @Override
    public boolean isValid(PaymentDto value, ConstraintValidatorContext context) {
        if(!value.getType().equals(PaymentType.TYPE1.toString())){ return true; }

        if(!value.getCurrency().equals(CurrencyType.EUR.toString())){ return false; }

        return value.getDetails() != null;
    }
}


