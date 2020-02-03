package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
