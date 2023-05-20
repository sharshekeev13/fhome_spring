package com.example.fhome.repository;

import com.example.fhome.domain.entity.Category;
import com.example.fhome.domain.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByCategory(Category category, Pageable pageable);
}
