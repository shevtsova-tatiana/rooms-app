package com.shevtsova.rooms.entity;

import javax.persistence.*;

@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    //@Basic(optional = false)
    private Integer id;

    private boolean lightState;

    public Integer getId() {
        return id;
    }

    public boolean isLightOn() {
        return lightState;
    }

    public void setLightState(boolean lightState) {
        this.lightState = lightState;
    }
}
