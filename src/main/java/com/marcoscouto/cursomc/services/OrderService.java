package com.marcoscouto.cursomc.services;

import com.marcoscouto.cursomc.domain.Order;
import com.marcoscouto.cursomc.repositories.OrderRepository;
import com.marcoscouto.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order findById(Integer id){
        Optional<Order> order = orderRepository.findById(id);
        return order.orElseThrow(() -> new ObjectNotFoundException("Object not found! Id: " + id +
                                                ", Type: " + Order.class.getName()));
    }

}
