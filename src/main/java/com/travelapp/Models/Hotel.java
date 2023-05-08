package com.travelapp.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "hotels_table")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String city;

    private int stars;

    public Hotel(String name, String city, int stars) {
        this.name = name;
        this.city = city;
        this.stars = stars;
    }
}
