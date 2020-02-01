package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Integer> {
}
