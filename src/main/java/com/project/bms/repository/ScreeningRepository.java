package com.project.bms.repository;

import com.project.bms.model.Auditorium;
import com.project.bms.model.Movie;
import com.project.bms.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {
    List<Screening> findScreeningByMovie(Long movieId);
    List<Screening> findDistinctAuditoriumByMovie(Movie movie);
}
