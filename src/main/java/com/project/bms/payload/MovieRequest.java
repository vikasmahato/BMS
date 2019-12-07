package com.project.bms.payload;

import com.project.bms.model.City;

public class MovieRequest {

    private enum MovieFilter {
        ALL,
        TRENDING,
        LATEST
    }

    private MovieFilter movieFilter;
    private City city;

    private String title;
    private String director;
    private String cast;
    private String description;
    private Integer duration;
    private String thumbUrl;
    private String imdbId;

    public MovieFilter getMovieFilter() {
        return movieFilter;
    }

    public void setMovieFilter(MovieFilter movieFilter) {
        this.movieFilter = movieFilter;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getThumbUrl() {
        return thumbUrl;
    }

    public void setThumbUrl(String thumbUrl) {
        this.thumbUrl = thumbUrl;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }
}
