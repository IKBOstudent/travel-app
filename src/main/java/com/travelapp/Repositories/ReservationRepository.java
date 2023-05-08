package com.travelapp.Repositories;

import com.travelapp.Models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    @Query("select r from Reservation r " +
            "where r.room.id = :roomId " +
            "and ((r.checkInDate < :checkOutDate and r.checkOutDate > :checkInDate)" +
            "or (r.checkOutDate > :checkInDate and r.checkInDate < :checkOutDate))")
    Reservation findFirstReservationCustom(Long roomId, LocalDate checkInDate, LocalDate checkOutDate);

    Reservation findFirstByRoom_Id(Long id);
}
