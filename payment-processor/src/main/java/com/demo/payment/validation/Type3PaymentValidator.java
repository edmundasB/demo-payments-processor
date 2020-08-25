package com.demo.payment.validation;

import com.demo.payment.model.PaymentDto;
import com.demo.payment.model.PaymentType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class Type3PaymentValidator implements ConstraintValidator<Type3PaymentValidate, PaymentDto> {
    @Override
    public boolean isValid(PaymentDto value, ConstraintValidatorContext context) {
        if(!value.getType().equals(PaymentType.TYPE3.toString())){
            return true;
        }
        return value.getBic() != null;
    }
}
