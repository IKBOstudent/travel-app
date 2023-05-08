package com.travelapp.Controllers;

import com.travelapp.Models.Hotel;
import com.travelapp.Services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("api/hotels")
//@CrossOrigin(origins = "http://localhost:8081")
public class HotelController {

    private final HotelService hotelService;

    @Autowired
    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<String>> getMetadata() {
        List<String> columns = new ArrayList<>();
        Field[] fields = Hotel.class.getDeclaredFields();

        for (Field field : fields) {
            columns.add(field.getName());
        }

        return ResponseEntity
                .ok()
                .body(columns);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Hotel>> getAllHotels() {

        return ResponseEntity
                .ok()
                .body(hotelService.getAllHotels());
    }

    @PostMapping()
    public ResponseEntity<Hotel> createHotel(@RequestBody Hotel hotel) {
        if (hotelService.createHotel(hotel)) {
            return ResponseEntity.ok().body(hotel);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHotel(@PathVariable Long id) {
        if (hotelService.deleteHotel(id)) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }


}