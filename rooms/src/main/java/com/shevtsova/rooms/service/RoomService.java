package com.shevtsova.rooms.service;

import com.shevtsova.rooms.entity.Room;
import com.shevtsova.rooms.repository.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    //TODO WHAT IS REFLECTION
    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room save(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> findById(Integer id) {
        return roomRepository.findById(id);
    }

    public List<Integer> findRoomIds() {
        List<Integer> idList = new ArrayList<>();
        roomRepository.findAll().forEach(room -> idList.add(room.getId()));
        return idList;
    }
}
