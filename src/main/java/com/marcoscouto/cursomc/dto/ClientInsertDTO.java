package com.marcoscouto.cursomc.dto;

import com.marcoscouto.cursomc.domain.Address;
import com.marcoscouto.cursomc.domain.City;
import com.marcoscouto.cursomc.services.validation.ClientInsert;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@ClientInsert
public class ClientInsertDTO implements Serializable {

    @NotEmpty(message = "Name not be empty")
    @Length(min = 5, max = 80, message = "Length must be between 5-80 characters")
    private String name;

    @NotEmpty(message = "Name not be empty")
    @Length(min = 5, max = 80, message = "Length must be between 5-80 characters")
    private String email;

    @NotEmpty(message = "Name not be empty")
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
