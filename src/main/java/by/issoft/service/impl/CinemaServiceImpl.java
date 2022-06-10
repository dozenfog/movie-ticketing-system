package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.cinema.Cinema;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.CinemaRepository;
import by.issoft.service.CinemaService;
import by.issoft.service.MovieRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CinemaServiceImpl implements CinemaService {
    private final CinemaRepository cinemaRepository;
    private final MovieRoomService movieRoomService;

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Cinema> findAll() {
        return cinemaRepository.findAll();
    }

    @Audit
    @Override
    public Cinema save(Cinema cinema) {
        cinema.setMovieRooms(cinema.getMovieRooms().stream().map(movieRoomService::save).toList());
        return cinemaRepository.save(cinema);
    }

    @Audit
    @Override
    public void delete(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException("Cinema with id " + id + " was not found.");
        }
        cinemaRepository.deleteById(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<Cinema> findById(Long id) {
        return cinemaRepository.findById(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return cinemaRepository.existsById(id);
    }
}
