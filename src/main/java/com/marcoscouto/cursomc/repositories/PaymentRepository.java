package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {
}
