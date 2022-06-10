package by.issoft.service;

import by.issoft.domain.cinema.Cinema;

public interface CinemaService extends CommonService<Cinema> {
    boolean existsById(Long id);
}
