package by.issoft.repository;

import by.issoft.domain.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends CommonRepository<User> {
    @Query("""
            select t.order.user from Ticket as t
            where t.order.event.movieRoom.cinema.id = :cinemaId
            and t.order.event.startDateTime between :startDateTime and :endDateTime
            group by t.order.user
            order by count(t.id) desc
            """)
    List<User> findTopByMovieRoomTickets(@Param("cinemaId") Long cinemaId,
                                         @Param("startDateTime") LocalDateTime startDateTime,
                                         @Param("endDateTime") LocalDateTime endDateTime);

    Optional<User> findByUserName(String username);
}
