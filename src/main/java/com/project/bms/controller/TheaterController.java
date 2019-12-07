package com.project.bms.controller;

import com.project.bms.model.Theater;
import com.project.bms.payload.ApiResponse;
import com.project.bms.payload.PagedResponse;
import com.project.bms.payload.TheaterRequest;
import com.project.bms.payload.TheaterResponse;
import com.project.bms.service.TheaterService;
import com.project.bms.util.AppConstants;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    private TheaterService theaterService;

    private Logger logger = Logger.getLogger(TheaterController.class);

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @GetMapping
    public PagedResponse<TheaterResponse> getTheaters(@RequestParam(value = "selectedCity") Long cityId,
                                                      @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                      @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return theaterService.getTheatersInCity(cityId, page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createTheater(@Valid @RequestBody TheaterRequest theaterRequest) {
        Theater theater = theaterService.createTheater(theaterRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{theaterId}")
                .buildAndExpand(theater.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Theater Created Successfully"));
    }

    @GetMapping("/{theaterId}")
    public TheaterResponse getPollById(@PathVariable Long theaterId) {
        return theaterService.getTheaterById(theaterId);
    }

    @GetMapping("/{movieId}/{cityId}")
    public PagedResponse<TheaterResponse> getTheatersForMovieInCity(@PathVariable Long movieId, @PathVariable Long cityId) {
        return theaterService.getTheatersForMovieInCity(movieId, cityId);
    }


}
