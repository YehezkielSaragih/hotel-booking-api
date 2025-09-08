package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.entity.Room;
import com.example.hotelbookingsystem.repository.BookingRepo;
import com.example.hotelbookingsystem.repository.RoomRepo;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepo bookingRepo;
    @Autowired
    private RoomRepo roomRepo;

    // POST /bookings buat booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        List<Room> validRooms = new ArrayList<>();

        if (booking.getRoomList() != null) {
            for (Room r : booking.getRoomList()) {
                roomRepo.findById(r.getRoomId()).ifPresent(room -> {
                    room.setAvailable(false);
                    roomRepo.save(room);
                    validRooms.add(room);
                });
            }
        }
        booking.setRoomList(validRooms);
        Booking savedBooking = bookingRepo.save(booking);
        return ResponseEntity.ok(savedBooking);
    }

    // GET /bookings list booking (filter by date)
    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(@RequestParam(required = false) LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(bookingRepo.findByDate(date));
        }
        return ResponseEntity.ok(bookingRepo.getAll());
    }

    // GET /bookings/{id} detail booking
    @GetMapping("/{id}")
    public ResponseEntity<Booking> findBookingById(@PathVariable Long id) {
        return bookingRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /bookings/{id} ubah tanggal booking
    @PutMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> updateBooking(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body
    ) {
        Booking booking = bookingRepo.findById(id).orElse(null);
        if (booking == null) {
            return ResponseEntity.notFound().build();
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            if (body.containsKey("checkIn")) {
                String checkInStr = body.get("checkIn").toString();
                booking.setCheckIn(sdf.parse(checkInStr));
            }
            if (body.containsKey("checkOut")) {
                String checkOutStr = body.get("checkOut").toString();
                booking.setCheckOut(sdf.parse(checkOutStr));
            }
        } catch (ParseException e) {
            return ResponseEntity.badRequest().build();
        }
        booking.setUpdatedAt(new java.util.Date());
        Booking updatedBooking = bookingRepo.save(booking);
        return ResponseEntity.ok(updatedBooking);
    }

    // DELETE /bookings/{id} batalkan booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Booking> cancelBooking(@PathVariable Long id) {
        Booking b = bookingRepo.findById(id).orElse(null);
        if (b == null) {
            return ResponseEntity.notFound().build();
        }
        b.setDeletedAt(new Date());
        bookingRepo.save(b);
        return ResponseEntity.ok(b);
    }
}
