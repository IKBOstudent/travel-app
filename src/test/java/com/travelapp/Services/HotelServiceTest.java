package com.travelapp.Services;

import com.travelapp.Models.Hotel;
import com.travelapp.Models.Room;
import com.travelapp.Repositories.HotelRepository;
import com.travelapp.Repositories.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@SpringBootTest
class HotelServiceTest {

    @InjectMocks
    private HotelService hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    private Hotel testHotel1;
    private Hotel testHotel2;
    private Room testRoom1;

    @BeforeEach
    public void setup() {
        testHotel1 = new Hotel(1L,
                                 "name",
                                 "a",
                                 3);
        testHotel2 = new Hotel(2L,
                                 "name",
                                 "a",
                                 4);
        testRoom1 = new Room(1L,
                               testHotel1,
                               2,
                               4000);
    }

    @Test
    void getAllHotels() {
        List<Hotel> list = new ArrayList<>();
        list.add(testHotel1);
        list.add(testHotel2);

        Mockito.when(hotelRepository.findAll()).thenReturn(list);

        List<Hotel> res = hotelService.getAllHotels();

        list.sort(Comparator.comparing(Hotel::getCity));
        Assertions.assertIterableEquals(list, res);
        Mockito.verify(hotelRepository, Mockito.times(1)).findAll();
    }

    @Test
    void createHotel() {
        Hotel hotel = testHotel1;

        Mockito.when(hotelRepository.save(Mockito.any(Hotel.class))).thenReturn(hotel);

        boolean res = hotelService.createHotel(hotel);

        Assertions.assertTrue(res);
        Mockito.verify(hotelRepository, Mockito.times(1)).save(Mockito.any(Hotel.class));
    }

    @Test
    void deleteHotel_ok() {
        Mockito.when(hotelRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.when(roomRepository.findFirstByHotel_Id(Mockito.any(Long.class))).thenReturn(null);
        Mockito.doNothing().when(hotelRepository).deleteById(Mockito.any(Long.class));

        boolean res = hotelService.deleteHotel(1L);

        Assertions.assertTrue(res);
        Mockito.verify(hotelRepository, Mockito.times(1)).existsById(Mockito.any(Long.class));
        Mockito.verify(roomRepository, Mockito.times(1)).findFirstByHotel_Id(Mockito.any(Long.class));
        Mockito.verify(hotelRepository, Mockito.times(1)).deleteById(Mockito.any(Long.class));
    }

    @Test
    void deleteHotel_bad() {
        Mockito.when(hotelRepository.existsById(Mockito.any(Long.class))).thenReturn(true);
        Mockito.when(roomRepository.findFirstByHotel_Id(Mockito.any(Long.class))).thenReturn(testRoom1);
        Mockito.doNothing().when(hotelRepository).deleteById(Mockito.any(Long.class));


        boolean res = hotelService.deleteHotel(1L);

        Assertions.assertFalse(res);
        Mockito.verify(hotelRepository, Mockito.times(1)).existsById(Mockito.any(Long.class));
        Mockito.verify(roomRepository, Mockito.times(1)).findFirstByHotel_Id(Mockito.any(Long.class));
        Mockito.verify(hotelRepository, Mockito.times(0)).deleteById(Mockito.any(Long.class));
    }
}