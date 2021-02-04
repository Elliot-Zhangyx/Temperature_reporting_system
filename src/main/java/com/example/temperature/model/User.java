package com.example.temperature.model;

import java.io.Serializable;

public class User implements Serializable {
    private Integer id;
    private String time;
    private String name;
    private String gender;
    private String temperature;
    private String place;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public User(Integer id, String time, String name, String gender, String temperature, String place) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.gender = gender;
        this.temperature = temperature;
        this.place = place;
    }
    public User(Integer id,String name, String gender, String temperature, String place) {
        this.id = id;
        this.time = time;
        this.name = name;
        this.gender = gender;
        this.temperature = temperature;
        this.place = place;
    }
    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", time='" + time + '\'' +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", temperature='" + temperature + '\'' +
                ", place='" + place + '\'' +
                '}';
    }
}
