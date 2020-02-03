package com.marcoscouto.cursomc.resources;

import com.marcoscouto.cursomc.domain.Order;
import com.marcoscouto.cursomc.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
