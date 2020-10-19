package com.smoothstack.lms.entity;

public class Genre {
    Integer id;
    String name;

    public Genre(int id, String name) { this.id = id; this.name = name; }

    public Genre(String name) { this.name = name; }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
