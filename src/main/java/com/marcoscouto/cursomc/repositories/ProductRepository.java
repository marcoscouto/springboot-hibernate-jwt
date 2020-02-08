package com.marcoscouto.cursomc.repositories;

import com.marcoscouto.cursomc.domain.Category;
import com.marcoscouto.cursomc.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {

//    @Query("SELECT DISTINCT obj from Product obj " +
//            "INNER JOIN obj.categories cat " +
//            "WHERE obj.name LIKE %:name% " +
//            "AND cat in :categories")
//    Page<Product> search(@Param("name") String nome,
//                         @Param("categories") List<Category> categories,
//                         Pageable pageRequest);

    @Transactional(readOnly = true)
    Page<Product> findDistinctByNameContainingAndCategoriesIn(String nome,
                                                              List<Category> categories,
                                                              Pageable pageRequest);
}
