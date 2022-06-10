package com.miniproject.etoko.repositories;

import com.miniproject.etoko.models.Shipment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, String> {
}