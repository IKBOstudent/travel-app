package com.travelapp.Controllers;

import com.travelapp.Models.Flight;
import com.travelapp.Models.Room;
import com.travelapp.Services.RoomService;
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
@RequestMapping("api/rooms")
@CrossOrigin(origins = "http://localhost:8081")
public class RoomController {

    private final RoomService roomService;

    @Autowired
    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public ResponseEntity<List<Room>> getRoom(
            @RequestParam(value = "city") String city,
            @RequestParam(value = "guests") int guests,
            @RequestParam(value = "check_in_date") LocalDate checkInDate,
            @RequestParam(value = "check_out_date") LocalDate checkOutDate
    ) {
        return ResponseEntity
                .ok()
                .body(roomService.getRooms(city, guests, checkInDate, checkOutDate));
    }

    @GetMapping("/metadata")
    public ResponseEntity<List<String>> getMetadata() {
        List<String> columns = new ArrayList<>();
        Field[] fields = Room.class.getDeclaredFields();

        for (Field field : fields) {
            columns.add(field.getName());
        }

        return ResponseEntity
                .ok()
                .body(columns);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms() {

        return ResponseEntity
                .ok()
                .body(roomService.getAllRooms());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping()
    public ResponseEntity<Room> createRoom(
            @RequestParam(value = "hotel_id") Long hotelId,
            @RequestBody Room room) {
        if (roomService.createRoom(hotelId, room)) {
            return ResponseEntity.ok().body(room);
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        if (roomService.deleteRoom(id)) {
            return ResponseEntity.ok().body("success");
        } else {
            return ResponseEntity.badRequest().body("failed");
        }
    }


}