package com.project.bms.service;

import com.project.bms.exception.ResourceNotFoundException;
import com.project.bms.model.Movie;
import com.project.bms.payload.MovieRequest;
import com.project.bms.payload.MovieResponse;
import com.project.bms.payload.PagedResponse;
import com.project.bms.repository.MovieRepository;
import com.project.bms.util.ValidatorUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public PagedResponse<MovieResponse> getMovies(int page, int size) {
        ValidatorUtils.validatePageNumberAndSize(page, size);
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
        Page<Movie> movies = movieRepository.findAll(pageable);

        if(movies.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), movies.getNumber(),
                    movies.getSize(), movies.getTotalElements(), movies.getTotalPages(), movies.isLast());
        }

        List<MovieResponse> movieResponses = movies.map(MovieResponse::new).getContent();

        return new PagedResponse<>(movieResponses, movies.getNumber(),
                movies.getSize(), movies.getTotalElements(), movies.getTotalPages(), movies.isLast());
    }

    public Movie createMovie(MovieRequest movieRequest) {
        Movie movie = new Movie(movieRequest);
        return movieRepository.save(movie);
    }

    public MovieResponse getMovieById(Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(
                () -> new ResourceNotFoundException("Movie", "id", movieId));

        return new MovieResponse(movie);
    }
}
