package com.travelapp.Services;

import com.travelapp.Models.Hotel;
import com.travelapp.Models.Room;
import com.travelapp.Repositories.HotelRepository;
import com.travelapp.Repositories.RoomRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RoomServiceTest {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

//    @Test
//    public void whenAddHotel_roomShouldBeAdded() {
//        Optional<Hotel> hotelOptional = hotelRepository.findById(1853L);
//        if (hotelOptional.isPresent()) {
//            System.out.println("present");
//            var hotel = hotelOptional.get();
//            var prev = roomRepository.count();
//            hotel.addRoom(new Room(2, 1220));
//            hotel.addRoom(new Room(3, 1500));
//            hotelRepository.save(hotel);
//
//            System.out.println(hotel.getRoomList());
//            Assertions.assertEquals(prev+2, roomRepository.count());
//
//        }
//    }

}