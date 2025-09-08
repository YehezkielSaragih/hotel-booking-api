package com.example.hotelbookingsystem.repository;

import com.example.hotelbookingsystem.entity.Booking;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BookingRepo {
    private final Map<Long, Booking> data = new ConcurrentHashMap<Long, Booking>();
    private final AtomicLong seq = new AtomicLong(0);

    public BookingRepo(){

    }

    public List<Booking> getAll() { return new ArrayList<Booking>(data.values()); }
    public Optional<Booking> findById(Long id) { return Optional.ofNullable(data.get(id)); }

    public List<Booking> findByDate(LocalDate date) {
        List<Booking> result = new ArrayList<>();
        for (Booking b : data.values()) {
            if (b.getCheckIn() != null && b.getCheckOut() != null) {
                LocalDate in = b.getCheckIn().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                LocalDate out = b.getCheckOut().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
                if ((date.isEqual(in) || date.isAfter(in)) && date.isBefore(out)) {
                    result.add(b);
                }
            }
        }
        return result;
    }

    public Booking save(Booking b) {
        if (b.getBookingId() == null) {
            b.setBookingId(seq.incrementAndGet());
            b.setCreatedAt(new Date());
        }
        data.put(b.getBookingId(), b);
        return b;
    }

    public void delete(Long id) { data.remove(id); }
    public boolean exists(Long id) { return data.containsKey(id); }
}
