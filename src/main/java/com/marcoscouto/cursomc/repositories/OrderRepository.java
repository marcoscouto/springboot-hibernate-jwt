package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
