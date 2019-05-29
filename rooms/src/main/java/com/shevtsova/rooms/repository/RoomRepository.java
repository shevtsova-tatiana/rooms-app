package com.shevtsova.rooms.repository;

import com.shevtsova.rooms.entity.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
}
