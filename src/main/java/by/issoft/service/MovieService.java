package by.issoft.service;

import by.issoft.domain.event.Movie;

public interface MovieService extends CommonService<Movie> {
    boolean existsById(Long id);
}
