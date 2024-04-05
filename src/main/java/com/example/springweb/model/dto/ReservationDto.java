package com.example.springweb.model.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ReservationDto {
    private Integer id;
    private Integer userId;
    private String email;
    private Integer vehicleId;
    private String name;
    private Float totalPrice;
    private LocalDate startDate;
    private LocalDate returnDate;
}
