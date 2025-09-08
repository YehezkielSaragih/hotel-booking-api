package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.repository.RoomRepo;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomRepo roomRepo;

    // POST /rooms tambah kamar
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = roomRepo.save(new Room(null, true, room.getRoomName(), new Date()));
        return ResponseEntity.ok(savedRoom);
    }

    // GET /rooms lihat semua kamar
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomRepo.findAll());
    }

    // GET /rooms/{id}/availability cek ketersediaan kamar
    @GetMapping("/{id}/availability")
    public ResponseEntity<String> checkAvailability(@PathVariable Long id) {
        Room tempRoom = roomRepo.findById(id).orElse(null);
        if(tempRoom==null) return ResponseEntity.notFound().build();

        if(tempRoom.isAvailable()) return ResponseEntity.ok("Available");
        return ResponseEntity.ok("Not Available");
    }
}
