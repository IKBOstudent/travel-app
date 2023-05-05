package com.travelapp.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;


@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
@Table(name="reservations_table")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "room_id")
    @JsonIdentityReference(alwaysAsId = true)
    private Room room;

    @Column(name = "check_in_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkInDate;

    @Column(name = "check_out_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate checkOutDate;

    public Reservation(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        this.room = room;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }

    public long getNights() {
        return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
    }
}
