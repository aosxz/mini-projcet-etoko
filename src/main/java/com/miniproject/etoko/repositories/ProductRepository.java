package com.miniproject.etoko.repositories;

import com.miniproject.etoko.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
}