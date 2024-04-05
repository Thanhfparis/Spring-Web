package com.example.springweb.model.entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Admin")
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String phone;
    private String password;
    private LocalDate lastUpdatedDate;

    @OneToMany(mappedBy = "lastUpdatedBy")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Reservation> reservations = new ArrayList<>();

}
