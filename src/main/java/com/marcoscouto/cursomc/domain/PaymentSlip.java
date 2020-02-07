package com.marcoscouto.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.marcoscouto.cursomc.domain.enums.StatePayment;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "tb_payment_slip")
public class PaymentSlip extends Payment {

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date dueDate;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date paymentDate;

    public PaymentSlip(){}

    public PaymentSlip(Integer id, Integer statePayment, Order order, Date dueDate, Date paymentDate) {
        super(id, StatePayment.toEnum(statePayment), order);
        this.dueDate = dueDate;
        this.paymentDate = paymentDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }


}
