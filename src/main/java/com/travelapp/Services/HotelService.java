package com.travelapp.Services;

import com.travelapp.Models.Hotel;
import com.travelapp.Repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HotelService {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        return hotelRepository.findAll();
    }

    public boolean createHotel(Hotel hotel) {
        hotelRepository.save(hotel);
        return true;
    }
}
