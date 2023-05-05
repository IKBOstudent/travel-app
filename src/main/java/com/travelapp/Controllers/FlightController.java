package com.travelapp.Controllers;

import com.travelapp.Models.Flight;
import com.travelapp.Services.FlightService;
import jakarta.persistence.Column;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/flights")
@CrossOrigin(origins = "http://localhost:8081")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping()
    public ResponseEntity<List<Flight>> getFlights(
            @RequestParam(value = "origin") String origin,
            @RequestParam(value = "destination") String destination,
            @RequestParam(value = "date") LocalDate date
    ) {
        return ResponseEntity
                .ok()
                .body(flightService.getFlights(origin, destination, date));
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<String>> getMetadata() {
        List<String> columns = new ArrayList<>();
        Field[] fields = Flight.class.getDeclaredFields();

        for (Field field : fields) {
            columns.add(field.getName());
        }

        return ResponseEntity
                .ok()
                .body(columns);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity
                .ok()
                .body(flightService.getAllFlights());
    }

    @PostMapping()
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        if (flightService.createFlight(flight)) {
            return ResponseEntity.ok().body(flight);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        if (flightService.deleteFlight(id)) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }


}
