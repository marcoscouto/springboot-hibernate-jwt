package com.marcoscouto.cursomc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.marcoscouto.cursomc.domain.enums.TypeClient;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "tb_client")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nome;
    private String email;
    private String document;
    private Integer typeClient;

    @JsonIgnoreProperties("client")
    @OneToMany(mappedBy = "client")
    private List<Address> addresses = new ArrayList<>();

    @ElementCollection
    @CollectionTable(name = "tb_phones")
    private Set<String> phones = new HashSet<>();

    @OneToMany(mappedBy = "client")
    private List<Order> orders = new ArrayList<>();

    public Client() {
    }

    public Client(Integer id, String nome, String email, String document, TypeClient typeClient) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.document = document;
        this.typeClient = typeClient.getCode();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocument() {
        return document;
    }

    public void setDocument(String document) {
        this.document = document;
    }

    public TypeClient getTypeClient() {
        return TypeClient.toEnum(typeClient);
    }

    public void setTypeClient(TypeClient typeClient) {
        this.typeClient = typeClient.getCode();
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public List<Order> getOrders() {
        return orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
