package com.travelapp.Services;

import com.travelapp.Models.Flight;
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
class RoomServiceTest {

    @InjectMocks
    private RoomService roomService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ReservationRepository reservationRepository;

    private Hotel testHotel1;
    private Room testRoom1;
    private Room testRoom2;
    private Reservation testReservation1;

    @BeforeEach
    public void setup() {
        testHotel1 = new Hotel(1L,
                               "name",
                               "a",
                               3);
        testRoom1 = new Room(1L,
                             testHotel1,
                             2,
                             4000);
        testRoom2 = new Room(2L,
                             testHotel1,
                             3,
                             5000);
        testReservation1 = new Reservation(testRoom1,
                                           LocalDate.of(2023, 5, 9),
                                           LocalDate.of(2023, 5, 12));
    }

    @Test
    void getAllRooms() {
        List<Room> list = new ArrayList<>();
        list.add(testRoom1);
        list.add(testRoom2);

        Mockito.when(roomRepository.findAll()).thenReturn(list);

        List<Room> res = roomService.getAllRooms();

        list.sort(Comparator.comparingLong(item -> item.getHotel().getId()));
        Assertions.assertIterableEquals(list, res);
        Mockito.verify(roomRepository, Mockito.times(1)).findAll();
    }

    @Test
    void getRooms() {
        List<Room> list = new ArrayList<>();
        list.add(testRoom1);
        list.add(testRoom2);
        var checkInDate = LocalDate.of(2023, 5, 9);
        var checkOutDate = LocalDate.of(2023, 5, 12);

        Mockito.when(roomRepository.findRoomCustom(
                Mockito.any(String.class),
                Mockito.anyInt())).thenReturn(list);

        Mockito.when(reservationRepository.findFirstReservationCustom(Mockito.any(Long.class),
                                                                      Mockito.any(LocalDate.class),
                                                                      Mockito.any(LocalDate.class)))
                             .thenReturn(null);

        List<Room> res = roomService.getRooms("a", 2, checkInDate, checkOutDate);

        Assertions.assertIterableEquals(list, res);

        Mockito.verify(roomRepository, Mockito.times(1))
                .findRoomCustom(Mockito.any(String.class), Mockito.anyInt());
        Mockito.verify(reservationRepository, Mockito.times(list.size()))
                .findFirstReservationCustom(Mockito.any(Long.class),
                                            Mockito.any(LocalDate.class),
                                            Mockito.any(LocalDate.class));
    }

    @Test
    void createRoom() {
        Room room = testRoom1;

        Mockito.when(roomRepository.save(Mockito.any(Room.class))).thenReturn(room);
        Mockito.when(hotelRepository.findById(Mockito.any(Long.class))).thenReturn(Optional.of(testHotel1));

        boolean res = roomService.createRoom(1L, room);

        Assertions.assertTrue(res);
        Mockito.verify(roomRepository, Mockito.times(1)).save(Mockito.any(Room.class));
        Mockito.verify(hotelRepository, Mockito.times(1)).findById(Mockito.any(Long.class));
    }

    @Test
    void deleteRoom_ok() {
        Mockito.when(roomRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.doNothing().when(roomRepository).deleteById(Mockito.any(Long.class));
        Mockito.when(reservationRepository.findFirstByRoom_Id(Mockito.any())).thenReturn(null);

        boolean res = roomService.deleteRoom(1L);

        Assertions.assertTrue(res);
        Mockito.verify(roomRepository, Mockito.times(1)).existsById(Mockito.any(Long.class));
        Mockito.verify(reservationRepository, Mockito.times(1)).findFirstByRoom_Id(Mockito.any(Long.class));
        Mockito.verify(roomRepository, Mockito.times(1)).deleteById(Mockito.any(Long.class));
    }

    @Test
    void deleteRoom_bad() {
        Mockito.when(roomRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.when(reservationRepository.findFirstByRoom_Id(Mockito.any(Long.class)))
                .thenReturn(testReservation1);
        Mockito.doNothing().when(roomRepository).deleteById(Mockito.any(Long.class));

        boolean res = roomService.deleteRoom(1L);

        Assertions.assertFalse(res);
        Mockito.verify(roomRepository, Mockito.times(1)).existsById(Mockito.any(Long.class));
        Mockito.verify(reservationRepository, Mockito.times(1)).findFirstByRoom_Id(Mockito.any(Long.class));
        Mockito.verify(roomRepository, Mockito.times(0)).deleteById(Mockito.any(Long.class));
    }
}