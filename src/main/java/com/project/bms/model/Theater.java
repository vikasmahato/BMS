package com.project.bms.model;

import com.project.bms.model.audit.UserDateAudit;
import com.project.bms.payload.TheaterRequest;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "theaters")
public class Theater extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @NotBlank
    @Size(max = 500)
    private String address;

    @NotBlank
    @Size(max = 100)
    private String name;

    @OneToMany(
            mappedBy = "theater",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 0, max = 10)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Auditorium> auditoriums = new ArrayList<>();

    public Theater() {
    }

    public Theater(TheaterRequest theaterRequest) {
        super();
        this.city = theaterRequest.getCity();
        this.address = theaterRequest.getAddress();
        this.name = theaterRequest.getName();
        this.auditoriums = theaterRequest.getAuditoriums();
    }

    public List<Auditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(List<Auditorium> auditoriums) {
        this.auditoriums = auditoriums;
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
}
