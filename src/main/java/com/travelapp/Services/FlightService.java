package com.travelapp.Services;

import com.travelapp.Models.Flight;
import com.travelapp.Repositories.FlightRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        List<Flight> found = flightRepository.findAll();
        log.info("get all flights success");
        return found;
    }

    public List<Flight> getFlights(
            String origin,
            String destination,
            LocalDate date
    ) {
        List<Flight> found = flightRepository.findByOriginDestinationDate(
                origin, destination, date
        );

        log.info("get flights " + origin + " - " + destination + " " + date + " success");
        return found;
    }

    public boolean createFlight(Flight flight) {
        try {
            flightRepository.save(flight);
            log.info("created " + flight);
            return true;
        } catch (Exception e) {
            log.error("error creating flight " + e);
            return false;
        }
    }

    public boolean deleteFlight(Long id) {
        if (flightRepository.existsById(id)) {
            log.info("deleted flight");
            flightRepository.deleteById(id);
            return true;
        }

        log.error("flight deleting failed: invalid id");
        return false;
    }
}
