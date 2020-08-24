package com.payment.demo.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = Type1PaymentValidator.class)
@Documented
public @interface Type1PaymentValidate {

    String message() default "Currency is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
