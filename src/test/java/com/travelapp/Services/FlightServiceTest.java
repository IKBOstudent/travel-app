package com.travelapp.Services;

import com.travelapp.Models.Flight;
import com.travelapp.Repositories.FlightRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class FlightServiceTest {

    @InjectMocks
    private FlightService flightService;

    @Mock
    private FlightRepository flightRepository;

    private Flight testFlight1;
    private Flight testFlight2;

    @BeforeEach
    public void setup() {
        testFlight1 = new Flight(1L,
                                 "a",
                                 "b",
                                 LocalDate.of(2023, 5, 10),
                                 LocalTime.of(9, 10),
                                 LocalTime.of(12, 20),
                                 1000);
        testFlight2 = new Flight(2L,
                                 "a",
                                 "b",
                                 LocalDate.of(2023, 5, 11),
                                 LocalTime.of(9, 10),
                                 LocalTime.of(12, 20),
                                 1000);
    }

    @Test
    void getAllFlights() {
        List<Flight> list = new ArrayList<>();
        list.add(testFlight1);
        list.add(testFlight2);

        Mockito.when(flightRepository.findAll()).thenReturn(list);

        List<Flight> res = flightService.getAllFlights();

        list.sort(Comparator.comparing(Flight::getOrigin));
        Assertions.assertIterableEquals(list, res);
        Mockito.verify(flightRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getFlightById() {
        Flight flight = testFlight1;

        Mockito.when(flightRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(flight));

        Flight res = flightService.getFlightById(1L);

        Assertions.assertEquals(res.getId(), 1L);
        Mockito.verify(flightRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    void getFlights() {
        List<Flight> list = new ArrayList<>();
        list.add(testFlight1);
        list.add(testFlight2);

        Mockito.when(flightRepository.findByOriginAndDestinationAndDate(
                Mockito.any(String.class),
                Mockito.any(String.class),
                Mockito.any(LocalDate.class))).thenReturn(list);

        List<Flight> res = flightService.getFlights("a", "b", LocalDate.of(2023, 5, 10));

        list.sort(Comparator.comparing(Flight::getDepartureTime));
        Assertions.assertIterableEquals(list, res);

        Mockito.verify(flightRepository, Mockito.times(1))
                .findByOriginAndDestinationAndDate(
                        Mockito.any(String.class),
                        Mockito.any(String.class),
                        Mockito.any(LocalDate.class));
    }


    @Test
    void createFlight() {
        Flight flight = testFlight1;

        Mockito.when(flightRepository.save(Mockito.any(Flight.class))).thenReturn(flight);

        boolean res = flightService.createFlight(flight);

        Assertions.assertTrue(res);
        Mockito.verify(flightRepository, Mockito.times(1)).save(Mockito.any(Flight.class));
    }

    @Test
    void deleteFlight() {
        Mockito.when(flightRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.doNothing().when(flightRepository).deleteById(Mockito.any(Long.class));

        boolean res = flightService.deleteFlight(1L);

        Assertions.assertTrue(res);
        Mockito.verify(flightRepository, Mockito.times(1)).existsById(Mockito.any(Long.class));
        Mockito.verify(flightRepository, Mockito.times(1)).deleteById(Mockito.any(Long.class));
    }
}