package com.example.springweb.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Data
@Table(name = "User")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fullName;
    private String email;
    private String password;
    private String phone;
    private String role;

    @OneToMany(mappedBy = "user")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Comment> comments = new ArrayList<>();

    private LocalDate lastUpdatedDate;

    @OneToMany(mappedBy = "sales")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();

}
