package com.shevtsova.rooms.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.shevtsova.rooms.entity.Room;
import com.shevtsova.rooms.service.NotificationService;
import com.shevtsova.rooms.service.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class RoomController {

    private RoomService roomService;
    private NotificationService notificationService;

    public RoomController(RoomService roomService, NotificationService notificationService) {
        this.roomService = roomService;
        this.notificationService = notificationService;
    }

    @GetMapping("/notifications/{id}")
    public SseEmitter subscribe(@PathVariable Integer id) {
        SseEmitter sseEmitter = new SseEmitter();
        notificationService.addEmitter(id, sseEmitter);

        sseEmitter.onCompletion(() -> notificationService.removeEmitter(id, sseEmitter));
        sseEmitter.onTimeout(() -> notificationService.removeEmitter(id, sseEmitter));

        return sseEmitter;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomService.findById(id);
        //noinspection OptionalIsPresent
        if (optionalRoom.isPresent())
            return new ResponseEntity<>(optionalRoom.get(), HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> setLightState(@PathVariable Integer id) {
        Optional<Room> optionalRoom = roomService.findById(id);
        if (optionalRoom.isPresent()) {
            Room room = optionalRoom.get();
            room.setLightState(!room.isLightOn());
            boolean LightState = room.isLightOn();
            Room savedRoom = roomService.save(room);
            if (savedRoom.isLightOn() == LightState) {
                notificationService.notifyRelatedClients(savedRoom);
                return new ResponseEntity<>(HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id_list")
    public ResponseEntity<List<Integer>> getIdList() {
        List<Integer> idList = roomService.findRoomIds();
        if (idList.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<>(idList, HttpStatus.OK);
    }

}
