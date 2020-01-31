package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

}
