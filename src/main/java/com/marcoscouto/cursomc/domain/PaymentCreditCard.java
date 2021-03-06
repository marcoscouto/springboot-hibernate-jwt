package com.marcoscouto.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.marcoscouto.cursomc.domain.enums.StatePayment;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tb_payment_credit_card")
@JsonTypeName("paymentCreditCard")
public class PaymentCreditCard extends Payment {
    
    private Integer numberOfInstallment;

    public PaymentCreditCard(){}

    public PaymentCreditCard(Integer id, Integer statePayment, Order order, Integer numberOfInstallment) {
        super(id, StatePayment.toEnum(statePayment), order);
        this.numberOfInstallment = numberOfInstallment;
    }

    public Integer getNumberOfInstallment() {
        return numberOfInstallment;
    }

    public void setNumberOfInstallment(Integer numberOfInstallment) {
        this.numberOfInstallment = numberOfInstallment;
    }


}
