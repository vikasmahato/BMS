package com.project.bms.repository;

import com.project.bms.model.City;
import com.project.bms.model.Theater;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheaterRepository extends JpaRepository<Theater, Long> {
    Optional<Theater> findById(Long theaterId);
    List<Theater> findByCity(City city);

    Page<Theater> findByCity(Long cityId, Pageable pageable);
}
