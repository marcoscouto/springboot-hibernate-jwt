package com.marcoscouto.cursomc.resources;

import com.marcoscouto.cursomc.domain.Product;
import com.marcoscouto.cursomc.dto.CategoryDTO;
import com.marcoscouto.cursomc.dto.ProductDTO;
import com.marcoscouto.cursomc.repositories.ProductRepository;
import com.marcoscouto.cursomc.resources.utils.URL;
import com.marcoscouto.cursomc.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/products")
public class ProductsResource {

    @Autowired
    private ProductService productService;

//    @GetMapping
//    public ResponseEntity<List<Product>> findAll(){
//        List<Product> products = productService.findAll();
//        return ResponseEntity.ok().body(products);
//    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> findById(@PathVariable Integer id){
        Product product = productService.findById(id);
        return ResponseEntity.ok().body(product);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> findPage(
            @RequestParam(value = "name", defaultValue = " ") String name,
            @RequestParam(value = "categories", defaultValue = "") String categories,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24")Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name")String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC")String direction){

        Page<ProductDTO> products = productService.search(URL.decodeParam(name), URL.decodeIntList(categories), page, linesPerPage, orderBy, direction).map(
                x -> new ProductDTO(x)
        );
        return ResponseEntity.ok().body(products);
    }

}
