package com.marcoscouto.cursomc.resources;

import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.domain.Order;
import com.marcoscouto.cursomc.dto.CategoryDTO;
import com.marcoscouto.cursomc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(value = "/orders")
public class OrderResource {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/{id}")
    public ResponseEntity<Order> findById(@PathVariable Integer id){
        Order order = orderService.findById(id);
        return ResponseEntity.ok().body(order);
    }

    @PostMapping
    public ResponseEntity<Order> insert(@Valid @RequestBody Order order){
        order = orderService.save(order);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(order.getId())
                .toUri();
        return ResponseEntity.created(uri).body(order);
    }

}
