package com.example.springweb.repository;

import com.example.springweb.model.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllBySales_Id(int id);
}
