package com.travelapp.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="rooms_table")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hotel_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Hotel hotel;

    private int beds;

    @Column(name = "night_price")
    private float nightPrice;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    @JsonIgnore
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
