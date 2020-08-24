package com.payment.demo.validation;

import com.payment.demo.model.CurrencyType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CurrencyValidator implements ConstraintValidator<CurrencyValidate, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        try {
            CurrencyType.valueOf(value);
        return true;
        } catch (Exception e){
            return false;
        }
    }
}


