package com.project.bms.model;

import com.project.bms.model.audit.UserDateAudit;
import com.project.bms.payload.MovieRequest;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movies")
public class Movie extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String title;

    @NotBlank
    @Size(max = 50)
    private String director;

    @NotBlank
    @Size(max = 500)
    private String cast;

    @NotBlank
    @Size(max = 100)
    private String description;

    @NotBlank
    private String imdbId;

    @NotNull
    private Integer duration;

    private String thumbUrl;

    @OneToMany(
            mappedBy = "movie",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Size(min = 0)
    @Fetch(FetchMode.SELECT)
    @BatchSize(size = 30)
    private List<Screening> screenings = new ArrayList<>();

    public Movie() {
    }

    public Movie(MovieRequest movieRequest) {
        this.title = movieRequest.getTitle();
        this.director = movieRequest.getDirector();
        this.cast = movieRequest.getCast();
        this.description = movieRequest.getDescription();
        this.duration = movieRequest.getDuration();
        this.thumbUrl = movieRequest.getThumbUrl();
        this.imdbId = movieRequest.getImdbId();
    }

    public Movie(MovieBuilder movieBuilder) {
        this.title = movieBuilder.title;
        this.director = movieBuilder.director;
        this.cast = movieBuilder.cast;
        this.description = movieBuilder.description;
        this.duration = movieBuilder.duration;
        this.thumbUrl = movieBuilder.thumbUrl;
        this.imdbId = movieBuilder.imdbId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
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

    public static class MovieBuilder {
        private String title;
        private String director;
        private String cast;
        private String description;
        private Integer duration;
        private String thumbUrl;
        private String imdbId;

        public MovieBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public MovieBuilder setDirector(String director) {
            this.director = director;
            return this;
        }

        public MovieBuilder setCast(String cast) {
            this.cast = cast;
            return this;
        }

        public MovieBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public MovieBuilder setDuration(Integer duration) {
            this.duration = duration;
            return this;
        }

        public MovieBuilder setThumbUrl(String thumbUrl) {
            this.thumbUrl = thumbUrl;
            return this;
        }

        public MovieBuilder setImdbId(String imdbId) {
            this.imdbId = imdbId;
            return this;
        }

        public Movie build() {
            return new Movie(this);
        }
    }
}
