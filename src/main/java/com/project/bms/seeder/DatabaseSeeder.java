package com.project.bms.seeder;

import com.project.bms.exception.AppException;
import com.project.bms.exception.ResourceNotFoundException;
import com.project.bms.model.*;
import com.project.bms.repository.*;
import com.project.bms.util.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class DatabaseSeeder {

    private UserRepository userRepository;
    private JdbcTemplate jdbcTemplate;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;
    private CountryRepository countryRepository;
    private CityRepository cityRepository;
    private MovieRepository movieRepository;
    private TheaterRepository theaterRepository;
    private AuditoriumRepository auditoriumRepository;
    private ScreeningRepository screeningRepository;

    private Logger logger = Logger.getLogger(DatabaseSeeder.class);

    @Autowired
    public DatabaseSeeder(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            RoleRepository roleRepository,
            CountryRepository countryRepository,
            CityRepository cityRepository,
            MovieRepository movieRepository,
            TheaterRepository theaterRepository,
            AuditoriumRepository auditoriumRepository,
            ScreeningRepository screeningRepository,
            JdbcTemplate jdbcTemplate) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.countryRepository = countryRepository;
        this.cityRepository = cityRepository;
        this.movieRepository = movieRepository;
        this.theaterRepository = theaterRepository;
        this.auditoriumRepository = auditoriumRepository;
        this.screeningRepository = screeningRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener
    public void seed(ContextRefreshedEvent event) {

        seedRolesTable();
        seedUsersTable();
        seedCountriesTable();
        seedCitiesTable();
        seedMoviesTable();
        seedTheaterRepository();
        seedAuditoriumTable();
        seedMoviesTable();
        seedScreeningTable();
        //seedScreeningTable();
    }

    private void seedRolesTable() {
        if(!roleRepository.existsByName(RoleName.ROLE_ADMIN)) {
            roleRepository.save(new Role(RoleName.ROLE_ADMIN));
            logger.info("Admin role created");
        } else {
            logger.trace("Admin role exists");
        }

        if(!roleRepository.existsByName((RoleName.ROLE_USER))) {
            roleRepository.save(new Role(RoleName.ROLE_USER));
            logger.info("User role created");
        } else {
            logger.trace("User role exists");
        }
    }

    private void seedUsersTable() {

        User user = new User("Vikas", "vikas",
                "vikasmahato0@gmail.com", passwordEncoder.encode("123456"));

        if(!(userRepository.existsByUsername(user.getUsername()) || userRepository.existsByEmail(user.getEmail()))) {
            Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                    .orElseThrow(() -> new AppException("User Role not set."));

            user.setRoles(Collections.singleton(userRole));

            User result = userRepository.save(user);
            logger.info("User table seeded");
        } else {
            logger.trace("User Seeding Not Required.");
        }
    }

    private void seedCountriesTable() {
        Country country = new Country("India");

        if(!countryRepository.existsByName(country.getName())) {
            countryRepository.save(country);
            logger.info("Country table seeded");
        } else {
            logger.trace("Country Seeding Not Required.");
        }

    }

    private void seedCitiesTable() {
        Country country = countryRepository.findByName("India")
                            .orElseThrow(() -> new ResourceNotFoundException("Country", "name", "India"));

        createCityIfNotExists(country, "Delhi");
        createCityIfNotExists(country, "Mumbai");
        createCityIfNotExists(country, "Chennai");
        createCityIfNotExists(country, "Kolkata");

    }

    private void createCityIfNotExists(Country country, String cityName) {
        if(!cityRepository.existsByNameAndCountry(cityName, country)) {
            cityRepository.save(new City(cityName, country));
            logger.info("City "+cityName+" created.");
        } else {
            logger.trace("City "+cityName+" already exists.");
        }
    }

    private void seedMoviesTable() {
        movieRepository.deleteAll();

        movieRepository.save(new Movie("Tadap", "Milan Luthria", "cast", "description", "tt10761694", 126, ""));
        movieRepository.save(new Movie("Antim: The Final Truth", "Milan Luthria", "cast", "description", "tt13491110", 126, ""));
        movieRepository.save(new Movie("Resident Evil: Welcome To Raccoon City", "Johannes Roberts", "cast", "description", "tt6920084", 126, ""));
        movieRepository.save(new Movie("Sooryavanshi", "Milan Luthria", "cast", "description", "tt9531772", 126, ""));
        movieRepository.save(new Movie("Satyameva Jayate 2", "Milan Luthria", "cast", "description", "tt10739666", 126, ""));
        movieRepository.save(new Movie("Teeja Punjab", "Milan Luthria", "cast", "description", "tt14203864", 126, ""));

       /* for(int i = 1; i<=27; i++) {
            movieRepository.save(new Movie.MovieBuilder()
                    .setTitle(StringUtils.getRandomString(20))
                    .setDuration(new Random().nextInt(200))
                    .setDirector(StringUtils.getRandomString(10))
                    .setDescription(StringUtils.getRandomString(60))
                    .setCast(StringUtils.getRandomString(20))
                    .setThumbUrl("http://localhost:3000/thumbnails/"+i+".jpg")
                    .setImdbId("tt4154796")
                    .build());
        }*/

        logger.info("Movie table seeded");
    }

    private void seedTheaterRepository() {
        List<City> cities = cityRepository.findAll();

        for (City city : cities) {
            for(int i = 0; i<5; i++) {
                Theater theater = new Theater();
                theater.setCity(city);
                theater.setAddress(StringUtils.getRandomString(100));
                theater.setName(StringUtils.getRandomString(20));
                theaterRepository.save(theater);
            }
        }
    }

    private void seedAuditoriumTable() {
        List<Theater> theaters = theaterRepository.findAll();

        for (Theater theater : theaters) {
            int n = (int)(4 * Math.random()) + 1;
            for(int i = 0; i<n; i++) {
                Auditorium auditorium = new Auditorium();
                auditorium.setTheater(theater);
                auditorium.setName(String.valueOf(i+1));
                auditorium.setSeatsNo(100);
                List<Seat> seats = new ArrayList<>();
                for(int j = 0; j< 100; j++) {
                    seats.add(new Seat(auditorium, j%10, j));
                }
                auditorium.setSeats(seats);
                auditoriumRepository.save(auditorium);
            }
        }
    }

    private void seedScreeningTable() {
        List<Movie> movies = movieRepository.findAll();
        List<Auditorium> auditoriums = auditoriumRepository.findAll();

        int numMovies = movies.size();
        int j = 0;

        for(Auditorium auditorium : auditoriums) {
            DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            String testDateString2 = "21-06-2019 09:00:00";
            Date screeningTime = null;
            try {
                screeningTime = df2.parse(testDateString2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            for(int i = 0; i<5; i++) {
                Screening screening = new Screening();
                screening.setAuditorium(auditorium);
                if(j == numMovies) {j = 0;}
                screening.setMovie(movies.get(j));

                screening.setScreeningStartTime(screeningTime);
                screeningRepository.save(screening);
                screeningTime = DateUtils.addMinutes(screeningTime, movies.get(j).getDuration());
                j++;
            }
        }
    }


}
