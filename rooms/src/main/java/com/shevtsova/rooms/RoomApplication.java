package com.shevtsova.rooms;

import com.shevtsova.rooms.entity.Room;
import com.shevtsova.rooms.service.RoomService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RoomApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RoomApplication.class, args);
        Room room1 = new Room();
        Room room2 = new Room();
        Room room3 = new Room();

        RoomService roomService = context.getBean(RoomService.class);
        roomService.save(room1);
        roomService.save(room2);
        roomService.save(room3);
    }

}
