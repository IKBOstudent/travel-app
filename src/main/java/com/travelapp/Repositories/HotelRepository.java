package com.travelapp.Repositories;


import com.travelapp.Models.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findHotelByCity(String city);
}
