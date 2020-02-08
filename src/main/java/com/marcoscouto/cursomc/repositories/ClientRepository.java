package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Transactional
    Client findByEmail(String email);

}
