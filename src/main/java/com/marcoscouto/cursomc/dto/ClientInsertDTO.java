package com.marcoscouto.cursomc.dto;

import com.marcoscouto.cursomc.domain.Address;
import com.marcoscouto.cursomc.domain.City;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClientInsertDTO implements Serializable {

    private String name;
    private String email;
    private String document;
    private Integer typeClient;

    List<Address> addresses = new ArrayList<>();

    private List<String> phones = new ArrayList<>();

    private City cityId;

    public ClientInsertDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public Integer getTypeClient() {
        return typeClient;
    }

    public void setTypeClient(Integer typeClient) {
        this.typeClient = typeClient;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public City getCityId() {
        return cityId;
    }

    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

    public List<String> getPhones() {
        return phones;
    }
}
