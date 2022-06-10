package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.cinema.Seat;
import by.issoft.repository.SeatRepository;
import by.issoft.service.SeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SeatServiceImpl implements SeatService {
    private final SeatRepository seatRepository;

    @Audit
    @Override
    public Seat save(Seat seat) {
        return seatRepository.save(seat);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<Seat> findAll() {
        return seatRepository.findAll();
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }

    @Audit
    @Override
    public void delete(Long id) {
        seatRepository.deleteById(id);
    }
}
