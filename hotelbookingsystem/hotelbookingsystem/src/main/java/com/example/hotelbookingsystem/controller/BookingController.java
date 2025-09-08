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
    public ResponseEntity<?> createBooking(@RequestBody Booking booking) {
        // Handle blank cust
        if(booking.getCustomerName().isEmpty()){
            return ResponseEntity.badRequest().build();
        }
        // Handle check in out
        if(booking.getCheckIn().after(booking.getCheckOut())){
            return ResponseEntity.badRequest().build();
        }
        // Handle availability
        for (Room r : booking.getRoomList()) {
            Room room = roomRepo.findById(r.getRoomId()).orElse(null);
            if (room == null) {
                return ResponseEntity.badRequest()
                        .body("Room with id " + r.getRoomId() + " does not exist");
            }
            if (!room.isAvailable()) {
                return ResponseEntity.badRequest()
                        .body("Room with id " + room.getRoomId() + " is not available");
            }
            // Set room as booked
            room.setAvailable(false);
            roomRepo.save(room);
        }
        Booking savedBooking = bookingRepo.save(booking);
        return ResponseEntity.ok(savedBooking);
    }

    // GET /bookings list booking (filter by date)
    @GetMapping
    public ResponseEntity<List<Booking>> getBookings(@RequestParam(required = false) String date) {
        Date targetDate = null;
        // Convert string date to date format
        if (date != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                targetDate = sdf.parse(date);
                System.out.println(targetDate);
            } catch (ParseException e) {
                return ResponseEntity.badRequest().body(null);
            }
        }
        // Return booking
        if (targetDate != null) {
            return ResponseEntity.ok(bookingRepo.findByDate(targetDate));
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
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        Booking b = bookingRepo.findById(id).orElse(null);
        if (b == null) {
            return ResponseEntity.notFound().build();
        }
        for (Room r : b.getRoomList()) {
            Room room = roomRepo.findById(r.getRoomId()).orElse(null);
            if(room == null) continue;
            room.setAvailable(true);
            roomRepo.save(room);
        }
        b.setDeletedAt(new Date());
        bookingRepo.save(b);
        return ResponseEntity.ok(b);
    }
}
