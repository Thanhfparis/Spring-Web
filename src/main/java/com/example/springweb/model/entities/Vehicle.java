package com.example.springweb.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Vehicle")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private Float price;
    private Float weight;
    private Float cylinder;
    private Float length;
    private String color;
    private Integer doorNumbers;

    @ManyToOne
    @JoinColumn(name = "vehicle_type_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private VehicleType vehicleType;

    @OneToMany(mappedBy = "vehicle")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    private LocalDate lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "sales_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User sales;

}
