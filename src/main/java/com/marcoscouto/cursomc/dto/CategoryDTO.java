package com.marcoscouto.cursomc.dto;

import com.marcoscouto.cursomc.domain.Category;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class CategoryDTO implements Serializable {

    private Integer id;

    @NotEmpty(message = "Name not be empty")
    @Length(min = 5, max = 80, message = "Length must be between 5-80 characters")
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(Category category) {
        this.id = category.getId();
        this.name = category.getName();
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

    public void setName(String name) {
        this.name = name;
    }
}
