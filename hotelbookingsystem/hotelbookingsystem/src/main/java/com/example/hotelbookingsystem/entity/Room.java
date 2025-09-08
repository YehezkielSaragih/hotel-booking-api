package com.example.hotelbookingsystem.entity;
import java.util.Date;
import java.util.List;

public class Room {

    private Long roomId;
    private boolean isAvailable;


    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    public Room(Long roomId, boolean isAvailable, Date createdAt) {
        this.roomId = roomId;
        this.isAvailable = isAvailable;
        this.createdAt = createdAt;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }
}
