package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.event.Event;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.EventRepository;
import by.issoft.service.EventService;
import by.issoft.service.MovieRoomService;
import by.issoft.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final MovieService movieService;
    private final MovieRoomService movieRoomService;

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    @Audit
    @Override
    public Event save(Event event) {
        if (!movieService.existsById(event.getMovie().getId())) {
            throw new NotFoundException("Movie with id " + event.getMovie().getId() + " was not found.");
        }
        if (!movieRoomService.existsById(event.getMovieRoom().getId())) {
            throw new NotFoundException("Movie room with id " + event.getMovieRoom().getId() + " was not found.");
        }
        return eventRepository.save(event);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Audit
    @Override
    public void delete(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException("Event with id " + id + " was not found.");
        }
        eventRepository.deleteById(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return eventRepository.existsById(id);
    }
}
