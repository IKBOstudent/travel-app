package com.travelapp.Services;

import com.travelapp.Models.Flight;
import com.travelapp.Models.Hotel;
import com.travelapp.Models.Reservation;
import com.travelapp.Models.Room;
import com.travelapp.Repositories.HotelRepository;
import com.travelapp.Repositories.ReservationRepository;
import com.travelapp.Repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class HotelService {
    private final HotelRepository hotelRepository;
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository, RoomRepository roomRepository, ReservationRepository reservationRepository) {
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
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
