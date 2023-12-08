package io.namoosori.tc.entity;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class Entity {
    //
    protected String id;

    protected Entity() {
        //
        this.id = UUID.randomUUID().toString();
    }

    protected Entity(String id) {
        //
        this.id = id;
    }
}
