package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StateRepository extends JpaRepository<State, Integer> {
}
