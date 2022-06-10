package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.cinema.MovieRoom;
import by.issoft.repository.MovieRoomRepository;
import by.issoft.service.MovieRoomService;
import by.issoft.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MovieRoomServiceImpl implements MovieRoomService {
    private final MovieRoomRepository movieRoomRepository;
    private final SeatService seatService;

    @Audit
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return movieRoomRepository.existsById(id);
    }

    @Audit
    @Override
    public MovieRoom save(MovieRoom movieRoom) {
        movieRoom.setSeats(movieRoom.getSeats().stream().map(seatService::save).toList());
        return movieRoomRepository.save(movieRoom);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<MovieRoom> findAll() {
        return movieRoomRepository.findAll();
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<MovieRoom> findById(Long id) {
        return movieRoomRepository.findById(id);
    }

    @Audit
    @Override
    public void delete(Long id) {
        movieRoomRepository.deleteById(id);
    }
}
