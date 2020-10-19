package com.smoothstack.lms.entity;

public class Publisher {
    Integer id;
    String name;
    String address;
    String publisherPhone;

    public Publisher(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public Publisher(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Publisher(String name, String address, String publisherPhone) {
        this.name = name;
        this.address = address;
        this.publisherPhone = publisherPhone;
    }

    public Publisher(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPublisherPhone() {
        return publisherPhone;
    }

    public void setPublisherPhone(String publisherPhone) {
        this.publisherPhone = publisherPhone;
    }

    @Override
    public String toString() {
        return name + (address == null ? "" : "Address: " + address);
    }
}
