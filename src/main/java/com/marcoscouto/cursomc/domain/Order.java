package com.marcoscouto.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "tb_order")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    private Date instant;

    @JsonIgnoreProperties("order")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "order") //Transfer the id for payment
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "address_delivery_id")
    private Address addressDelivery;

    @OneToMany(mappedBy = "id.order")
    private Set<OrderItem> items = new HashSet<>();

    public Order() {
    }

    public Order(Integer id, Date instant, Client client, Address addressDelivery) {
        this.id = id;
        this.instant = instant;
        this.client = client;
        this.addressDelivery = addressDelivery;
    }

    public Double getTotal(){
        return items.stream().mapToDouble(x -> x.getSubtotal()).sum();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInstant() {
        return instant;
    }

    public void setInstant(Date instant) {
        this.instant = instant;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getAddressDelivery() {
        return addressDelivery;
    }

    public void setAddressDelivery(Address addressDelivery) {
        this.addressDelivery = addressDelivery;
    }

    public Set<OrderItem> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        final StringBuffer sb = new StringBuffer();
        sb.append("Order: ");
        sb.append(getId());
        sb.append(", Instant: ");
        sb.append(sdf.format(getInstant()));
        sb.append(", Cliente: ");
        sb.append(getClient().getName());
        sb.append(", Payment situation: ");
        sb.append(getPayment().getStatePayment().getDescription());
        sb.append("\nDetails: \n");
        getItems().forEach(x -> sb.append(x));
        sb.append("Total: ");
        sb.append(nf.format(getTotal()));
        return sb.toString();
    }
}
