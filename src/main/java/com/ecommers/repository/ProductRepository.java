package com.ecommers.repository;

import com.ecommers.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<List<Product>> findAllByCategory_Id(Integer integer);
}
