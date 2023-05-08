package com.travelapp.Services;

import com.travelapp.Models.Reservation;
import com.travelapp.Models.Room;
import com.travelapp.Repositories.ReservationRepository;
import com.travelapp.Repositories.RoomRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
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
        found.sort(Comparator.comparingLong(item -> item.getRoom().getId()));
        log.info("get all reservations success");
        return found;
    }

    public boolean createReservation(Long roomId, Reservation reservation) {
        if (reservation.getCheckInDate().isEqual(reservation.getCheckOutDate()) ||
                reservation.getCheckInDate().isAfter(reservation.getCheckOutDate())
        ) {
            log.error("invalid time period");
            return false;
        }

        Optional<Room> roomOptional = roomRepository.findById(roomId);
        if (roomOptional.isPresent()) {
            Reservation sameDateReservation = reservationRepository.findFirstReservationCustom(
                    roomId, reservation.getCheckInDate(), reservation.getCheckOutDate());
            if (sameDateReservation == null) {
                Room room = roomOptional.get();
                reservation.setRoom(room);

                reservationRepository.save(reservation);

                log.info("created reservation " + reservation);
                return true;
            }
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
