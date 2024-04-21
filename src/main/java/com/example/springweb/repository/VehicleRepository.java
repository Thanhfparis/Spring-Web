package com.example.springweb.repository;

import com.example.springweb.model.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllBySales_Id(int id);

    Integer countAllByVehicleType_Id(int id);

    @Query("SELECT COUNT(v) FROM Vehicle v WHERE v.sales.id = :userId")
    Integer countByUserId(@Param("userId") Integer userId);
}
