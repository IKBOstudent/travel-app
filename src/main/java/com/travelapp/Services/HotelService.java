package com.travelapp.Services;

import com.travelapp.Models.Flight;
import com.travelapp.Models.Hotel;
import com.travelapp.Repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class HotelService {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> found =  hotelRepository.findAll();
        log.info("get all hotels success");
        return found;
    }

    public boolean createHotel(Hotel hotel) {
        try {
            hotelRepository.save(hotel);
            log.info("created " + hotel);
            return true;
        } catch (Exception e) {
            log.error("error creating hotel " + e);
            return false;
        }
    }

    public boolean deleteHotel(Long id) {
        if (hotelRepository.existsById(id)) {
            log.info("deleted hotel");
            hotelRepository.deleteById(id);
            return true;
        }

        log.error("hotel deleting failed: invalid id");
        return false;
    }
}
