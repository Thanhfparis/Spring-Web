package com.example.springweb.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "VehicleType")
public class VehicleType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private LocalDate lastUpdatedDate;

    @OneToMany(mappedBy = "vehicleType")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();

}
