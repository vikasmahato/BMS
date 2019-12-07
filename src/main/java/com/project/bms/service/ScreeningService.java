package com.project.bms.service;

import com.project.bms.model.Auditorium;
import com.project.bms.model.Movie;
import com.project.bms.model.Screening;
import com.project.bms.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningService {

    private ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public List<Screening> getDistinctAuditoriumsForMovie(Long movieId) {
        Movie movie = new Movie();
        movie.setId(movieId);
        return  screeningRepository.findDistinctAuditoriumByMovie(movie);
    }
}
