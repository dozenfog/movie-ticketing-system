package by.issoft.controller.utils.suppliers;

import by.issoft.domain.cinema.MovieRoom;
import by.issoft.domain.event.Event;
import by.issoft.domain.event.EventStatus;
import by.issoft.domain.event.Movie;

import java.time.LocalDateTime;

public class EventSupplier {
    public static String getEventJson() {
        return "{" +
                "\"startDateTime\": \"2022-01-08T12:30:00\"," +
                "    \"movieId\": 1," +
                "    \"movieRoomId\": 1" +
                "}";
    }

    public static String getUpdateEventJson() {
        return "{" +
                "\"startDateTime\": \"2022-01-08T12:30:00\"," +
                "    \"eventStatus\": 2" +
                "}";
    }

    public static Event getEvent() {
        return Event.builder()
                .startDateTime(LocalDateTime.now())
                .movieRoom(MovieRoom.builder()
                        .id(1L)
                        .build())
                .eventStatus(EventStatus.SCHEDULED)
                .movie(Movie.builder().id(1L).build())
                .build();
    }

    public static Event getUpdatedEvent() {
        return Event.builder()
                .startDateTime(LocalDateTime.parse("2022-01-08T12:30:00"))
                .movieRoom(MovieRoom.builder()
                        .id(1L)
                        .build())
                .eventStatus(EventStatus.ACTIVE)
                .movie(Movie.builder().id(1L).build())
                .build();
    }
}
