package com.example.springweb.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Agency")
public class Agency {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String agencyName;

    @OneToMany(mappedBy = "agency")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();

}
