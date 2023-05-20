package com.example.fhome.repository;


import com.example.fhome.domain.entity.Product;
import com.example.fhome.domain.entity.Reviews;
import com.example.fhome.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews,Long> {
    Reviews findByUserAndProduct(User user, Product product);

    List<Reviews> findAllByProduct(Product product);
}
