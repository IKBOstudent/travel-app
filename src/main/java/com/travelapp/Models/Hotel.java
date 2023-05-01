package com.travelapp.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="hotels_table")
public class Hotel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String city;

    private int stars;

    @OneToMany(mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> roomList = new ArrayList<>();

    public Hotel() {
    }

    public Hotel(Long id, String city, int stars) {
        this.id = id;
        this.city = city;
        this.stars = stars;
    }

    public void addRoom(Room room) {
        this.roomList.add(room);
    }

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", city='" + city + '\'' +
                ", stars=" + stars +
                ", roomList=" + roomList +
                '}';
    }
}
