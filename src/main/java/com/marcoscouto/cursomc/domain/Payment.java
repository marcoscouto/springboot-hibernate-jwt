package com.marcoscouto.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.marcoscouto.cursomc.domain.enums.StatePayment;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "tb_payment")
@Inheritance(strategy = InheritanceType.JOINED)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "@type")
public abstract class Payment implements Serializable {

    @Id
    private Integer id;
    private Integer statePayment;

    @JsonIgnoreProperties("payment")
    @OneToOne
    @JoinColumn(name = "order_id")
    @MapsId //Same id as Order
    private Order order;

    public Payment() {
    }

    public Payment(Integer id, StatePayment statePayment, Order order) {
        this.id = id;
        this.statePayment = statePayment == null ? null : statePayment.getCode();
        this.order = order;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StatePayment getStatePayment() {
        return StatePayment.toEnum(statePayment);
    }

    public void setStatePayment(StatePayment statePayment) {
        this.statePayment = statePayment.getCode();
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
