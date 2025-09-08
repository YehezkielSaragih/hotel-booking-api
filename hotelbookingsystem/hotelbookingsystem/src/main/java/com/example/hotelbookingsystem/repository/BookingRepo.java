package com.example.hotelbookingsystem.repository;

import com.example.hotelbookingsystem.entity.Booking;
import com.example.hotelbookingsystem.entity.Booking;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class BookingRepo {
    private final Map<Long, Booking> data = new ConcurrentHashMap<Long, Booking>();
    private final AtomicLong seq = new AtomicLong(0);

    public BookingRepo(){

    }

    public List<Booking> findAll() { return new ArrayList<Booking>(data.values()); }
    public Optional<Booking> findById(Long id) { return Optional.ofNullable(data.get(id)); }
    public Booking save(Booking b) {
        if (b.getBookingId() == null) b.setBookingId(seq.incrementAndGet());
        data.put(b.getBookingId(), b);
        return b;
    }

    public void delete(Long id) { data.remove(id); }
    public boolean exists(Long id) { return data.containsKey(id); }
}
