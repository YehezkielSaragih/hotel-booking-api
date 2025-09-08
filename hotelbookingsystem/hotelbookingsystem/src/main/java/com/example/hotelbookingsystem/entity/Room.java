package com.example.hotelbookingsystem.entity;
import java.util.Date;
import java.util.List;

public class Room {

    // Attribute
    private Long roomId;
    private boolean isAvailable;
    private String roomName;
    // Standard attribute
    private Date createdAt;
    private Date updatedAt;
    private Date deletedAt;

    // Constructor
    public Room(Long roomId, boolean isAvailable, String roomName, Date createdAt) {
        this.roomId = roomId;
        this.isAvailable = isAvailable;
        this.roomName = roomName;
        this.createdAt = createdAt;
    }

    // Setter Getter
    public String getRoomName() {
        return roomName;
    }
    public void setRoomName(String roomName) {
        this.roomName = roomName;
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
