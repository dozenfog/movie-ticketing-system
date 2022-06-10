package by.issoft.repository;

import by.issoft.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CommonRepository<User> {
    @Query("""
            select o.user from Ticket as t
            inner join t.order as o
            inner join o.event as e
            inner join e.movieRoom as mv
            inner join mv.cinema as c
            where c.id = :cinemaId
            and e.startDateTime >= :startDateTime
            and e.startDateTime <= :endDateTime
            group by o.user
            order by count(t.id) desc
            """)
    List<User> findTopByMovieRoomTickets(@Param("cinemaId") Long cinemaId,
                                         @Param("startDateTime") LocalDateTime startDateTime,
                                         @Param("endDateTime") LocalDateTime endDateTime);

    Optional<User> findByUserName(String username);
}
