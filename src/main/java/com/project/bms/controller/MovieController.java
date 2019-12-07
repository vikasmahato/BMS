package com.project.bms.controller;

import com.project.bms.model.Movie;
import com.project.bms.payload.ApiResponse;
import com.project.bms.payload.MovieRequest;
import com.project.bms.payload.MovieResponse;
import com.project.bms.payload.PagedResponse;
import com.project.bms.service.MovieService;
import com.project.bms.util.AppConstants;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    private MovieService movieService;

    private Logger logger = Logger.getLogger(MovieController.class);

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public PagedResponse<MovieResponse> getMovies(
                                                    @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return movieService.getMovies(page, size);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createMovie(@Valid @RequestBody MovieRequest movieRequest) {
        Movie movie = movieService.createMovie(movieRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{movieId}")
                .buildAndExpand(movie.getId()).toUri();

        return ResponseEntity.created(location)
                .body(new ApiResponse(true, "Theater Created Successfully"));
    }

    @GetMapping("/{movieId}")
    public MovieResponse getPollById(@PathVariable Long movieId) {
        return movieService.getMovieById(movieId);
    }
}
