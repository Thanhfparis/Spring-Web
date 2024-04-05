package com.example.springweb.model.dto;

import lombok.Data;

@Data
public class VehicleDto {
    private Integer id;
    private String name;
    private Float price;
    private Float weight;
    private Float cylinder;
    private Float length;
    private String color;
    private Integer doorNumbers;
    private Integer vehicleTypeId;
    private String vehicleTypeName;
    private Integer userId;
    private String fullName;
}
