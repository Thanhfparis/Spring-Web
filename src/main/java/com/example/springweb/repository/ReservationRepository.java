package com.example.springweb.repository;

import com.example.springweb.model.entities.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByUser_Id(Integer id);
    List<Reservation> findAllByVehicle_Sales_Id(Integer id);
}
