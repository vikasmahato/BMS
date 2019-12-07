package com.project.bms.service;

import com.project.bms.exception.ResourceNotFoundException;
import com.project.bms.model.Auditorium;
import com.project.bms.model.Screening;
import com.project.bms.model.Theater;
import com.project.bms.payload.PagedResponse;
import com.project.bms.payload.TheaterRequest;
import com.project.bms.payload.TheaterResponse;
import com.project.bms.repository.TheaterRepository;
import com.project.bms.util.ValidatorUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class TheaterService {

    private TheaterRepository theaterRepository;
    private ScreeningService screeningService;

    public TheaterService(TheaterRepository theaterRepository, ScreeningService screeningService) {
        this.theaterRepository = theaterRepository;
        this.screeningService = screeningService;
    }

    public PagedResponse<TheaterResponse> getTheatersInCity(Long cityId, int page, int size) {
        ValidatorUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Theater> theaters = theaterRepository.findByCity(cityId, pageable);

        if(theaters.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), theaters.getNumber(),
                    theaters.getSize(), theaters.getTotalElements(), theaters.getTotalPages(), theaters.isLast());
        }

        List<TheaterResponse> theaterResponses = theaters.map(TheaterResponse::new).getContent();

        return new PagedResponse<>(theaterResponses, theaters.getNumber(),
                theaters.getSize(), theaters.getTotalElements(), theaters.getTotalPages(), theaters.isLast());
    }

    public Theater createTheater(TheaterRequest theaterRequest) {
        Theater theater = new Theater(theaterRequest);

        return theaterRepository.save(theater);

    }

    public TheaterResponse getTheaterById(Long theaterId) {
        Theater theater = theaterRepository.findById(theaterId).orElseThrow(
                () -> new ResourceNotFoundException("Theater", "id", theaterId));

        return new TheaterResponse(theater);
    }

    @Transactional
    public PagedResponse<TheaterResponse> getTheatersForMovieInCity(Long movieId, Long cityId) {
        List<Screening> screenings = screeningService.getDistinctAuditoriumsForMovie(movieId);

        List<Auditorium> auditoriums = new ArrayList<>();

        for(Screening screening : screenings) {
            auditoriums.add(screening.getAuditorium());
        }

        Set<TheaterResponse> theaters = new HashSet<>();

        for(Auditorium auditorium : auditoriums) {
            if(auditorium.getTheater().getCity().getId() == cityId) {
                theaters.add(new TheaterResponse(auditorium.getTheater(), Boolean.TRUE, Boolean.TRUE));
            }
        }

        List<TheaterResponse> theaterResponses = new ArrayList<>(theaters);

        return new PagedResponse<>(theaterResponses, theaters.size(),
                theaters.size(), theaters.size(), 1, Boolean.TRUE);
    }
}
