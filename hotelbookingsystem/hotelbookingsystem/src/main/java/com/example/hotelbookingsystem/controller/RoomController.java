package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.repository.RoomRepo;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class RoomController {

    @Autowired
    private RoomRepo roomRepo;

    // POST /rooms tambah kamar
    @PostMapping
    public ResponseEntity<Room> createRoom(@RequestBody Room room) {
        Room savedRoom = roomRepo.save(room);
        return ResponseEntity.ok(savedRoom);
    }

    // GET /rooms lihat semua kamar
    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomRepo.findAll());
    }

    // GET /rooms/{id}/availability cek ketersediaan kamar
//    @GetMapping("/{id}/availability")
//    public ResponseEntity<Boolean> checkAvailability(
//            @PathVariable Long id,
//            @RequestParam LocalDate checkIn,
//            @RequestParam LocalDate checkOut) {
//        boolean available = roomRepo.checkAvailability(id, checkIn, checkOut);
//        return ResponseEntity.ok(available);
//    }
}
