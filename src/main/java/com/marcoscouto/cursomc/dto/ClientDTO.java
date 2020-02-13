package com.marcoscouto.cursomc.dto;

import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.services.validation.ClientUpdate;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@ClientUpdate
public class ClientDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Name can't be empty")
    @Length(min = 5, max = 120, message = "Length must be between 5-120 characters")
    private String name;

    @Email(message = "Invalid email")
    private String email;

    public ClientDTO() {
    }

    public ClientDTO(Client client) {
        this.id = client.getId();
        this.name = client.getName();
        this.email = client.getName();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setNome(String nome) {
        this.name = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
