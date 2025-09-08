package com.example.hotelbookingsystem.repository;

import com.example.hotelbookingsystem.entity.Room;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RoomRepo {

    private final Map<Long, Room> data = new ConcurrentHashMap<Long, Room>();
    private final AtomicLong seq = new AtomicLong(0);

    public RoomRepo(){
        save( new Room(null, true, "Twin", new Date() ));
        save( new Room(null, true, "King", new Date() ));
        save( new Room(null, true, "Queen", new Date() ));
        save( new Room(null, true, "Twin King", new Date() ));
        save( new Room(null, true, "Twin Queen", new Date() ));
    }

    public List<Room> findAll() { return new ArrayList<Room>(data.values()); }
    public Optional<Room> findById(Long id) { return Optional.ofNullable(data.get(id)); }
    public Room save(Room r) {
        if (r.getRoomId() == null) r.setRoomId(seq.incrementAndGet());
        data.put(r.getRoomId(), r);
        return r;
    }

    public void delete(Long id) {
        Room r = findById(id).orElse(null);
        if(r == null) return;

        r.setDeletedAt(new Date());
        data.put(r.getRoomId(), r);
    }

    public boolean exists(Long id) { return data.containsKey(id); }
}
