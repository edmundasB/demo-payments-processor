package com.payment.demo.validation;

import com.payment.demo.model.PaymentDto;
import com.payment.demo.model.PaymentType;

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
