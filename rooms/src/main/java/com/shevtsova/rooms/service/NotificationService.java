package com.shevtsova.rooms.service;

import com.shevtsova.rooms.entity.Room;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final Map<Integer, List<SseEmitter>> emittersMap = new HashMap<>();

    public void addEmitter(Integer roomId, SseEmitter emitter) {
        if (emittersMap.get(roomId) == null)
            emittersMap.put(roomId, new ArrayList<>());
        emittersMap.get(roomId).add(emitter);
    }

    public void removeEmitter(Integer roomId, SseEmitter emitter) {
        emittersMap.get(roomId).remove(emitter);
    }

    // TODO: READ ABOUT @Async annotation
    public void notifyRelatedClients(Room room) {
        List<SseEmitter> deadEmitters = new ArrayList<>();
        for (SseEmitter emitter : emittersMap.get(room.getId())) {
            try {
                    emitter.send(SseEmitter.event().data(room));
            } catch (Exception e) {
                deadEmitters.add(emitter);
            }
        }
        emittersMap.get(room.getId()).removeAll(deadEmitters);
    }
}
