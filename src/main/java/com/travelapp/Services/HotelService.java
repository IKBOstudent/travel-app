package com.travelapp.Services;

import com.travelapp.Models.Hotel;
import com.travelapp.Repositories.HotelRepository;
import com.travelapp.Repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@Slf4j
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    public List<Hotel> getAllHotels() {
        List<Hotel> found = hotelRepository.findAll();
        found.sort(Comparator.comparing(Hotel::getCity));
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
            System.out.println(roomRepository.findFirstByHotel_Id(id));
            if (roomRepository.findFirstByHotel_Id(id) != null) {
                log.error("cant delete hotel as rooms still exist");
                return false;
            }
            log.info("deleted hotel");
            hotelRepository.deleteById(id);
            return true;
        }

        log.error("hotel deleting failed: invalid id");
        return false;
    }
}
