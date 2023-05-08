package com.travelapp.Services;

import com.travelapp.Models.Hotel;
import com.travelapp.Models.Reservation;
import com.travelapp.Models.Room;
import com.travelapp.Repositories.HotelRepository;
import com.travelapp.Repositories.ReservationRepository;
import com.travelapp.Repositories.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private RoomRepository roomRepository;

    private Reservation testReservation1;
    private Reservation testReservation2;
    private Room testRoom1;

    @BeforeEach
    public void setup() {

        testRoom1 = new Room(1L,
                             new Hotel(),
                             2,
                             4000);
        testReservation1 = new Reservation(testRoom1,
                                           LocalDate.of(2023, 5, 9),
                                           LocalDate.of(2023, 5, 12));

        testReservation2 = new Reservation(testRoom1,
                                           LocalDate.of(2023, 5, 13),
                                           LocalDate.of(2023, 5, 15));

    }

    @Test
    void getAllReservations() {
        List<Reservation> list = new ArrayList<>();
        list.add(testReservation1);
        list.add(testReservation2);

        Mockito.when(reservationRepository.findAll()).thenReturn(list);

        List<Reservation> res = reservationService.getAllReservations();

        list.sort(Comparator.comparingLong(item -> item.getRoom().getId()));
        Assertions.assertIterableEquals(list, res);
        Mockito.verify(reservationRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createReservation() {
        Mockito.when(roomRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(testRoom1));
        Mockito.when(reservationRepository.findFirstReservationCustom(Mockito.any(Long.class),
                                                                       Mockito.any(LocalDate.class),
                                                                       Mockito.any(LocalDate.class)))
                .thenReturn(null);
        Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(testReservation1);

        boolean res = reservationService.createReservation(1L, testReservation1);

        Assertions.assertTrue(res);
        Mockito.verify(roomRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
        Mockito.verify(reservationRepository, Mockito.times(1)).findFirstReservationCustom(Mockito.any(Long.class),
                                                                                           Mockito.any(LocalDate.class),
                                                                                           Mockito.any(LocalDate.class));
        Mockito.verify(reservationRepository, Mockito.times(1)).save(Mockito.any(Reservation.class));
    }

    @Test
    void deleteReservation() {
        Mockito.when(reservationRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.doNothing().when(reservationRepository).deleteById(Mockito.any(Long.class));

        boolean res = reservationService.deleteReservation(1L);

        Assertions.assertTrue(res);
        Mockito.verify(reservationRepository, Mockito.times(1)).existsById(Mockito.any(Long.class));
        Mockito.verify(reservationRepository, Mockito.times(1)).deleteById(Mockito.any(Long.class));
    }
}