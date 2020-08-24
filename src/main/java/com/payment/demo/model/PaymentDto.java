package com.payment.demo.model;

import com.payment.demo.validation.CurrencyValidate;
import com.payment.demo.validation.PaymentTypeValidate;
import com.payment.demo.validation.Type1PaymentValidate;
import com.payment.demo.validation.Type3PaymentValidate;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Type1PaymentValidate
@Type3PaymentValidate
public class PaymentDto {
    @PaymentTypeValidate
    @NotNull
    private String type;
    @DecimalMin(value = "0.00", inclusive = false)
    @NotNull
    private BigDecimal amount;
    @CurrencyValidate
    @NotNull
    private String currency;
    @NotNull
    private String debtorIban;
    @NotNull
    private String creditorIban;
    private String details;
    private String bic;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    public void setDebtorIban(String debtorIban) {
        this.debtorIban = debtorIban;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public void setCreditorIban(String creditorIban) {
        this.creditorIban = creditorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDetails() {
        return details;
    }

    public String getBic() {
        return bic;
    }

    public void setBic(String bic) {
        this.bic = bic;
    }
}
