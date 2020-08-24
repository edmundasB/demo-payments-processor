package com.payment.demo.controller;

import com.payment.demo.validation.PaymentTypeValidate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CancelPaymentDto {
    @NotNull
    @NotBlank
    private String paymentId;
    @PaymentTypeValidate
    @NotNull
    private String type;

    public CancelPaymentDto() {
    }

    public CancelPaymentDto(String paymentId, @NotNull String type) {
        this.paymentId = paymentId;
        this.type = type;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
