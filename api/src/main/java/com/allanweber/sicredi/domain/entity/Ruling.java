package com.allanweber.sicredi.domain.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection ="ruling")
public class Ruling {

    public Ruling(String name) {
        this.name = name;
    }

    @Id
    private ObjectId id;

    private String name;

    public ObjectId getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ruling ruling = (Ruling) o;
        return Objects.equals(id, ruling.id) &&
                Objects.equals(name, ruling.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
