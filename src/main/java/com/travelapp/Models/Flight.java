package com.travelapp.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "flights_table")
public class Flight {
    @Id
    @GeneratedValue
    private Long id;

    private String origin;

    private String destination;

    public Flight() {
    }

    public Flight(Long id, String origin, String destination) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
    }
}
