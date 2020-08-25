package com.demo.payment.validation;

import com.demo.payment.model.CurrencyType;
import com.demo.payment.model.PaymentDto;
import com.demo.payment.model.PaymentType;

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


