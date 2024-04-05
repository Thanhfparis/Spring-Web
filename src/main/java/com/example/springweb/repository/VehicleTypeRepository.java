package com.example.springweb.repository;

import com.example.springweb.model.entities.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Integer> {

}
