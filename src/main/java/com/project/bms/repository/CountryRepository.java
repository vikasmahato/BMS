package com.project.bms.repository;

import com.project.bms.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
    Boolean existsByName(String name);

    @Override
    Optional<Country> findById(Long countryId);

    Optional<Country> findByName(String name);

    List<Country> findByIdIn(List<Long> countryIds);
}
