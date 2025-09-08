package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.repository.BookingRepo;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
public class BookingController {

    @Autowired
    private BookingRepo bookingRepo;

    // POST /bookings buat booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingRepo.save(booking);
        return ResponseEntity.ok(savedBooking);
    }

    // GET /bookings list booking (filter by date)
    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(@RequestParam(required = false) LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(bookingRepo.findByDate(date));
        }
        return ResponseEntity.ok(bookingRepo.findAll());
    }

    // GET /bookings/{id} detail booking
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return bookingRepo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT /bookings/{id} ubah tanggal booking
    @PatchMapping(path="/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Booking> patch(@PathVariable Long id, @RequestBody Map<String, Object> body) {
        Booking b = bookingRepo.findById(id).orElse(null);
        if (b == null) return ResponseEntity.notFound().build();
        if (body.containsKey("checkIn")) {
            b.setCheckIn(java.sql.Date.valueOf(body.get("checkIn"))); // format yyyy-MM-dd
        }
        if (body.containsKey("checkOut")) {
            b.setCheckOut(java.sql.Date.valueOf(body.get("checkOut"))); // format yyyy-MM-dd
        }
        b.setUpdatedAt(new Date()); // update timestamp
        return ResponseEntity.ok(bookingRepo.save(b));
    }

    // DELETE /bookings/{id} batalkan booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingRepo.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
