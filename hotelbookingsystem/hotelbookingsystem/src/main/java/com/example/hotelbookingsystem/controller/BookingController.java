package com.example.hotelbookingsystem.controller;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.repository.BookingRepo;
import org.springframework.beans.factory.annotation.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.*;
import java.time.LocalDate;
import java.util.List;

@RestController
public class BookingController {

    @Autowired
    private Booking bookingRepo;

    // POST /bookings buat booking
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        Booking savedBooking = bookingRepo.create(booking);
        return ResponseEntity.ok(savedBooking);
    }

    // GET /bookings list booking (filter by date)
    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(@RequestParam(required = false) LocalDate date) {
        if (date != null) {
            return ResponseEntity.ok(bookingRepo.getByDate(date));
        }
        return ResponseEntity.ok(bookingRepo.getAll());
    }

    // GET /bookings/{id} detail booking
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingRepo.getById(id));
    }

    // PUT /bookings/{id} ubah tanggal booking
    @PutMapping("/{id}")
    public ResponseEntity<Booking> updateBookingDate(
            @PathVariable Long id,
            @RequestParam LocalDate newStartDate,
            @RequestParam LocalDate newEndDate) {
        Booking updated = bookingRepo.update(id, newStartDate, newEndDate);
        return ResponseEntity.ok(updated);
    }

    // DELETE /bookings/{id} batalkan booking
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        bookingRepo.cancel(id);
        return ResponseEntity.noContent().build();
    }
}
