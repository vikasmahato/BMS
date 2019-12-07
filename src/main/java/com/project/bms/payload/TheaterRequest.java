package com.project.bms.payload;

import com.project.bms.model.Auditorium;
import com.project.bms.model.City;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class TheaterRequest {
    private City city;

    @NotBlank
    @Size(max = 500)
    private String address;

    @NotBlank
    @Size(max = 100)
    private String name;

    @NotNull
    @Valid
    private List<Auditorium> auditoriums = new ArrayList<>();

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
