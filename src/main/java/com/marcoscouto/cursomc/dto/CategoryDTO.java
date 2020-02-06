package com.marcoscouto.cursomc.dto;

import com.marcoscouto.cursomc.domain.Category;

import java.io.Serializable;

public class CategoryDTO implements Serializable {

    private Integer id;
    private String nome;

    public CategoryDTO() {
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.nome = category.getName();
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
}
