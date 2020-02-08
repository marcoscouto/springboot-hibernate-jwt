package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.domain.Client;
import com.marcoscouto.cursomc.domain.Product;
import com.marcoscouto.cursomc.repositories.CategoryRepository;
import com.marcoscouto.cursomc.repositories.ProductRepository;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public Product findById(Integer id){
        Optional<Product> product = productRepository.findById(id);
        return product.orElseThrow(() -> new ObjectNotFoundException("Product not found! Id: " + id +
                ", Type: " + Client.class.getName()));
    }

    public Page<Product> search(String name,
                                List<Integer> ids,
                                Integer page,
                                Integer linesPerPage,
                                String orderBy,
                                String direction){

        PageRequest pageRequest = PageRequest.of(
                page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);

        List<Category> categories = categoryRepository.findAllById(ids);

        return productRepository.findDistinctByNameContainingAndCategoriesIn(name, categories, pageRequest);

    }
}
