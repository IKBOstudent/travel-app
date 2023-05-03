package com.travelapp.Services;

import com.travelapp.Models.Hotel;
import com.travelapp.Models.Reservation;
import com.travelapp.Models.Room;
import com.travelapp.Repositories.HotelRepository;
import com.travelapp.Repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Room> getAllRooms() {
        List<Room> found = roomRepository.findAll();
        log.info("get all rooms success");
        return found;
    }

    public boolean createRoom(Long hotelId, Room room) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            hotel.addRoom(room);
            room.setHotel(hotel);

            hotelRepository.save(hotel);
//            roomRepository.save(room);

            log.info("created room");
            return true;
        }

        log.error("error creating room");
        return false;
    }

    public boolean deleteRoom(Long id) {
        if (roomRepository.existsById(id)) {
            log.info("deleted room");
            roomRepository.deleteById(id);
            return true;
        }

        log.error("room deleting failed: invalid id");
        return false;
    }
}
