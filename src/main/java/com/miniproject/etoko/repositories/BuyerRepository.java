package com.miniproject.etoko.repositories;

import com.miniproject.etoko.models.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Integer> {
}