package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.repositories.CategoryRepository;
import com.marcoscouto.cursomc.services.exceptions.DataIntegrityException;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public Category findById(Integer id){
        Optional<Category> category = categoryRepository.findById(id);
        return category.orElseThrow(() ->
                new ObjectNotFoundException("Object not found! Id: " + id +
                        ", Type: " + Category.class.getName()));
    }

    public Category save(Category obj){
        obj.setId(null);
        return categoryRepository.save(obj);
    }

    public Category update(Integer id, Category obj){
        findById(id);
        return categoryRepository.save(obj);
    }

    public void delete(Integer id){
        findById(id);
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("It's not possible delete a category with products.");
        }

    }

}
