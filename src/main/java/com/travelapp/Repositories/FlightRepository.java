package com.travelapp.Repositories;

import com.travelapp.Models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    @Query("select s from Flight s "
            + "where (s.origin = :origin) "
            + "and (s.destination = :destination) "
            + "and (s.date = :date) ")
    List<Flight> find(String origin, String destination, LocalDate date);
}
