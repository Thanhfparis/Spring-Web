package com.example.springweb.repository;

import com.example.springweb.model.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

    List<Vehicle> findAllBySales_Id(int id);

    Integer countAllByVehicleType_Id(int id);

    @Query("select sum(ve.price) from Vehicle ve where ve.vehicleType.id in (1, 2)")
    Long countTotalPriceOf2Wheels();

    @Query("select sum(ve.price) from Vehicle ve where ve.vehicleType.id in (3, 4)")
    Long countTotalPriceOf4Wheels();
}
