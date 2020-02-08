package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Transactional(readOnly = true)
    Client findByEmail(String email);

}
