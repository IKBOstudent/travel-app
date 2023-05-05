package com.travelapp.Services;

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
import java.util.Optional;

@Service
@Slf4j
public class RoomService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final HotelRepository hotelRepository;

    @Autowired
    public RoomService(RoomRepository roomRepository, ReservationRepository reservationRepository, HotelRepository hotelRepository) {
        this.roomRepository = roomRepository;
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
    }

    public List<Room> getAllRooms() {
        List<Room> found = roomRepository.findAll();
        log.info("get all rooms success");
        return found;
    }

    public List<Room> getRooms(
            String city,
            int guests,
            LocalDate checkInDate,
            LocalDate checkOutDate
    ) {

        List<Room> roomsResult = new ArrayList<>();
        List<Room> rooms = roomRepository.findRoomCustom(city, guests);
        for (Room room : rooms) {
            System.out.println("room" + room);
            List<Reservation> reservations = reservationRepository.findReservationCustom(room.getId(), checkInDate, checkOutDate);
            if (reservations.size() == 0) {
                roomsResult.add(room);
            }
        }

        log.info("get rooms in city " + city + " for " + guests + " guests success");
        return roomsResult;
    }

    public boolean createRoom(Long hotelId, Room room) {
        Optional<Hotel> hotelOptional = hotelRepository.findById(hotelId);
        if (hotelOptional.isPresent()) {
            Hotel hotel = hotelOptional.get();
            room.setHotel(hotel);

            roomRepository.save(room);

            log.info("created room " + room);
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
