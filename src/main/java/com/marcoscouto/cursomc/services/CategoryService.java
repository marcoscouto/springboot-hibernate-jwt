package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.dto.CategoryDTO;
import com.marcoscouto.cursomc.repositories.CategoryRepository;
import com.marcoscouto.cursomc.services.exceptions.DataIntegrityException;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        Category category = findById(id);
        updateData(category, obj);
        return categoryRepository.save(category);
    }

    public void delete(Integer id){
        findById(id);
        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new DataIntegrityException("It's not possible delete a category with products.");
        }
    }

    public Page<Category> findPage(Integer page, Integer linesPerPage, String orderBy, String direction){
        PageRequest pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return categoryRepository.findAll(pageRequest);
    }

    public Category fromDTO(CategoryDTO categoryDTO){
       return new Category(categoryDTO.getId(), categoryDTO.getName());

    }

    private void updateData(Category category, Category obj) {
        if(obj.getName() != null) category.setName(obj.getName());
    }

}
