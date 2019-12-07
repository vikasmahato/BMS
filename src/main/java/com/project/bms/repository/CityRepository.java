package com.project.bms.repository;

import com.project.bms.model.City;
import com.project.bms.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    Optional<City> findByName(String cityName);

    List<City> findByCountry(Country country);

    Boolean existsByNameAndCountry(String cityName, Country country);

    List<City> findAll();
}
