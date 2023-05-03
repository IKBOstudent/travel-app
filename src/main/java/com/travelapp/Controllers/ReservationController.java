package com.travelapp.Controllers;


import com.travelapp.Models.Flight;
import com.travelapp.Models.Reservation;
import com.travelapp.Services.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/reservations")
@CrossOrigin(origins = "http://localhost:8081")
public class ReservationController {

    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Reservation>> getAllReservations() {

        return ResponseEntity
                .ok()
                .body(reservationService.getAllReservations());
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<String>> getMetadata() {
        List<String> columns = new ArrayList<>();
        Field[] fields = Reservation.class.getDeclaredFields();

        for (Field field : fields) {
            columns.add(field.getName());
        }

        return ResponseEntity
                .ok()
                .body(columns);
    }

//    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<Reservation> createReservation(
            @RequestParam(value = "room_id") Long roomId,
            @RequestBody Reservation reservation) {
        if (reservationService.createReservation(roomId, reservation)) {
            return ResponseEntity.ok().body(reservation);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id) {
        if (reservationService.deleteReservation(id)) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }


}