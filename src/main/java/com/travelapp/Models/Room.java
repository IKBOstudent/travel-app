package com.travelapp.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="rooms_table")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonIgnore
    private Hotel hotel;

    private int beds;

    @Column(name = "night_price")
    private float nightPrice;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();

    public Room() {
    }

    public Room(Long id, Hotel hotel, int beds) {
        this.id = id;
        this.hotel = hotel;
        this.beds = beds;
    }

    public void addReservation(Reservation reservation) {
        this.reservationList.add(reservation);
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", hotel=" + hotel +
                ", beds=" + beds +
                ", reservationList=" + reservationList +
                '}';
    }
}
