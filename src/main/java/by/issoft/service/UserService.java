package by.issoft.service;

import by.issoft.domain.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserService extends CommonService<User>, UserDetailsService {
    boolean existsById(Long id);

    List<User> findTopByMovieRoomTickets(Long cinemaId, LocalDate startDate, LocalDate endDate);

    Optional<User> findByUsername(String username);
}
