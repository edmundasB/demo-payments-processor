package com.demo.payment.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(validatedBy = PaymentTypeValidator.class)
@Documented
public @interface PaymentTypeValidate {

    String message() default "Payment type is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
