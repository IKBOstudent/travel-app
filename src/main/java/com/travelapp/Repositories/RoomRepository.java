package com.travelapp.Repositories;

import com.travelapp.Models.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r " +
            "where r.hotel.city = :city and r.beds >= :guests ")
    List<Room> findRoomCustom(String city, int guests);
}
