package by.issoft.service.impl;

import by.issoft.audit.Audit;
import by.issoft.domain.user.User;
import by.issoft.exception.NotFoundException;
import by.issoft.repository.UserRepository;
import by.issoft.service.CinemaService;
import by.issoft.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final CinemaService cinemaService;

    public static final String ROLE_PREFIX = "ROLE_";

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Audit
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Audit
    @Override
    public void delete(Long id) {
        if (!existsById(id)) {
            throw new NotFoundException("User with id " + id + " was not found.");
        }
        userRepository.deleteById(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return userRepository.existsById(id);
    }

    @Audit
    @Override
    @Transactional(readOnly = true)
    public List<User> findTopByMovieRoomTickets(Long cinemaId, LocalDate startDate, LocalDate endDate) {
        if (!cinemaService.existsById(cinemaId)) {
            throw new NotFoundException("Cinema with id " + cinemaId + " was not found.");
        }
        return userRepository.findTopByMovieRoomTickets(cinemaId, LocalDateTime.of(startDate, LocalTime.MIN),
                LocalDateTime.of(endDate, LocalTime.MAX));
    }

    @Audit
    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Audit
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return findByUsername(username).map(user -> {
                    GrantedAuthority authority = new SimpleGrantedAuthority(ROLE_PREFIX + user.getRole().name());
                    return new org.springframework.security.core.userdetails.User(
                            user.getUserName(),
                            user.getPassword(),
                            List.of(authority));
                })
                .orElseThrow(() -> new NotFoundException("User with username " + username + " was not found."));
    }
}
