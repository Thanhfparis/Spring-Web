package com.example.springweb.model.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "Reservation")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Float totalPrice;

    private LocalDate startDate;

    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private User user;

    private LocalDate lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "last_updated_by")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Admin lastUpdatedBy;
}