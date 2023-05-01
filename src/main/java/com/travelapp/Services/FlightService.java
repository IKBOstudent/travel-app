package com.travelapp.Services;

import com.travelapp.Models.Flight;
import com.travelapp.Repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    public boolean createFlight(Flight flight) {
        flightRepository.save(flight);
        return true;
    }
}
