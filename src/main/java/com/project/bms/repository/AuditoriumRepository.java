package com.project.bms.repository;

import com.project.bms.model.Auditorium;
import com.project.bms.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuditoriumRepository extends JpaRepository<Auditorium, Long> {
    List<Auditorium> findByTheater(Theater theater);

}
