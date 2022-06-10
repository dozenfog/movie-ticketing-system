package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.event.Movie;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.MovieRepository;
import by.issoft.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Audit
    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    @Audit
    @Override
    public void delete(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException("Movie with id " + id + " was not found.");
        }
        movieRepository.deleteById(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return movieRepository.existsById(id);
    }
}
