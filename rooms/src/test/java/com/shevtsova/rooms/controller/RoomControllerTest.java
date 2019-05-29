package com.shevtsova.rooms.controller;

import com.shevtsova.rooms.entity.Room;
import com.shevtsova.rooms.service.RoomService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RoomControllerTest {

    private RoomController roomController;
    @Mock
    private RoomService roomService;
    @Mock
    private Room room;
    @Mock
    private Room savedRoom;

    @Before
    public void setUp() throws Exception {
        roomController = new RoomController(roomService, null);
    }

    @Test
    public void getRoomById_invalidId() {
        when(roomService.findById(100)).thenReturn(Optional.empty());
        ResponseEntity<?> actualResponseEntity = roomController.getRoomById(100);
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void getRoomById_roomIsNotEmpty() {
        when(roomService.findById(1)).thenReturn(Optional.of(room));
        ResponseEntity<?> actualResponseEntity = roomController.getRoomById(1);
        ResponseEntity<?> expectedResponseEntity = new ResponseEntity<>(room, HttpStatus.OK);
        assertEquals(expectedResponseEntity, actualResponseEntity);
    }

    @Test
    public void setLightState_roomIsNotEmpty() {
        when(roomService.findById(1)).thenReturn(Optional.of(room));
        ResponseEntity<?> actualResponseEntity = roomController.getRoomById(1);
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());
    }

    @Test
    public void setLightState_invalidId() {
        when(roomService.findById(100)).thenReturn(Optional.empty());
        ResponseEntity<?> actualResponseEntity = roomController.setLightState(100);
        assertEquals(HttpStatus.NOT_FOUND, actualResponseEntity.getStatusCode());
    }

//    @Test
//    public void setLightState_roomSave() {
//        when(roomService.findById(1)).thenReturn(Optional.of(room));
//        when(room.isLightOn()).thenCallRealMethod();
//        doCallRealMethod().when(room).setLightState(anyBoolean());
//        when(roomService.save(room)).thenReturn(savedRoom);
//        roomController.setLightState(1);
//        verify(room).setLightState(true);
//        // TODO: add return tests
//    }

    @Test
    public void getIdList() {
    }
}