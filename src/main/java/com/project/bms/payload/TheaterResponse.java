package com.project.bms.payload;

import com.project.bms.model.Auditorium;
import com.project.bms.model.City;
import com.project.bms.model.Theater;

import java.util.ArrayList;
import java.util.List;

public class TheaterResponse {
    private Long id;
    private City city;
    private String address;
    private String name;
    private List<Auditorium> auditoriums = new ArrayList<>();

    public TheaterResponse(Theater theater) {
        this.id = theater.getId();
        this.city = theater.getCity();
        this.address = theater.getAddress();
        this.name = theater.getName();
        this.auditoriums = theater.getAuditoriums();
    }

    public TheaterResponse(Theater theater, Boolean withoutCity, Boolean withoutAuditoriums) {
        this.id = theater.getId();
        this.address = theater.getAddress();
        this.name = theater.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }
}
