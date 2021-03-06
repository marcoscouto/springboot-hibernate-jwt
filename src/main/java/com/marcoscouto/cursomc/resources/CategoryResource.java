package com.marcoscouto.cursomc.resources;

import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.dto.CategoryDTO;
import com.marcoscouto.cursomc.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryResource {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll(){
        List<CategoryDTO> categories = new ArrayList<>();
        categoryService.findAll().forEach(
                x -> categories.add(new CategoryDTO(x))
        );
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/page")
    public ResponseEntity<Page<CategoryDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){
        Page<CategoryDTO> categories = categoryService.findPage(page, linesPerPage, orderBy, direction).map(
                x -> new CategoryDTO(x)
        );
        return ResponseEntity.ok().body(categories);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Category> findById(@PathVariable Integer id){
       Category category = categoryService.findById(id);
       return ResponseEntity.ok().body(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping
    public ResponseEntity<Category> insert(@Valid @RequestBody CategoryDTO categoryDTO){
        Category category = categoryService.fromDTO(categoryDTO);
        category = categoryService.save(category);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(category.getId())
                .toUri();
        return ResponseEntity.created(uri).body(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Category> update(@Valid @RequestBody CategoryDTO categoryDTO, @PathVariable Integer id){
        Category category = categoryService.fromDTO(categoryDTO);
        category.setId(id);
        category = categoryService.update(id, category);
        return ResponseEntity.ok().body(category);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
