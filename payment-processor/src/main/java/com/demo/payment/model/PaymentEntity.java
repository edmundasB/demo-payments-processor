package com.demo.payment.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "PAYMENTS")
public class PaymentEntity {
    @Id
    private String id = UUID.randomUUID().toString();
    private String status;
    private String type;
    private BigDecimal amount;
    private String currency;
    private String debtorIban;
    private String creditorIban;
    private String details;
    private String bic;
    private BigDecimal fee;
    private Boolean notified;

    @CreationTimestamp
    @Column(name = "create_date")
    private LocalDateTime createDate;
    @UpdateTimestamp
    @Column(name = "modify_date")
    private LocalDateTime modifyDate;

    public PaymentEntity(PaymentDto paymentDto) {
        this.status = PaymentStatus.PROCEED.toString();
        this.type = paymentDto.getType();
        this.amount = paymentDto.getAmount();
        this.currency = paymentDto.getCurrency();
        this.debtorIban = paymentDto.getDebtorIban();
        this.creditorIban = paymentDto.getCreditorIban();
        this.details = paymentDto.getDetails();
        this.bic = paymentDto.getBic();
        this.notified = false;
    }

    private  PaymentEntity() {
    }

    public String getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public String getType() {
        return type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getDebtorIban() {
        return debtorIban;
    }

    public String getCreditorIban() {
        return creditorIban;
    }

    public String getDetails() {
        return details;
    }

    public String getBic() {
        return bic;
    }

    public LocalDateTime getModifyDate() {
        return modifyDate;
    }

    public String getStatus() {
        return status;
    }

    public void cancel() {
        this.status = PaymentStatus.CANCELED.toString();
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public boolean getNotified() {
        return notified;
    }

    public void setNotified() {
        this.notified = true;
    }
}
