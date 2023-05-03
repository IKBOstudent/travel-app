package com.travelapp.Services;

import com.travelapp.Models.Hotel;
import com.travelapp.Models.Reservation;
import com.travelapp.Models.Room;
import com.travelapp.Repositories.ReservationRepository;
import com.travelapp.Repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final RoomRepository roomRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.roomRepository = roomRepository;
    }

    public List<Reservation> getAllReservations() {
        List<Reservation> found = reservationRepository.findAll();
        log.info("get all reservations success");
        return found;
    }

    public boolean createReservation(Long roomId, Reservation reservation) {
        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isPresent()) {
            Room room = roomOptional.get();
            room.addReservation(reservation);
            reservation.setRoom(room);

            roomRepository.save(room);
//            reservationRepository.save(reservation);

            log.info("created reservation");
            return true;
        }

        log.error("error creating reservation");
        return false;
    }

    public boolean deleteReservation(Long id) {
        if (reservationRepository.existsById(id)) {
            log.info("deleted reservation");
            reservationRepository.deleteById(id);
            return true;
        }

        log.error("reservation deleting failed: invalid id");
        return false;
    }
}
