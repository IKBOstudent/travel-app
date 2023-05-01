package com.travelapp.Controllers;

import com.travelapp.Models.Flight;
import com.travelapp.Services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
            @RequestParam(value = "origin", required = true) String origin,
            @RequestParam(value = "destination", required = true) String destination,
            @RequestParam(value = "date", required = true) LocalDate date
    ) {
        System.out.println(origin + " " + destination + " " + date);
        return ResponseEntity.ok(flightService.getFlights(
                origin,
                destination,
                date
        ));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Flight>> getAllFlights() {
        return ResponseEntity.ok(flightService.getAllFlights());
    }

    @PostMapping()
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {
        if (flightService.createFlight(flight)) {
            return ResponseEntity.ok(flight);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }


}
