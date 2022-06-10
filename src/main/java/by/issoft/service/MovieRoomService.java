package by.issoft.service;

import by.issoft.domain.cinema.MovieRoom;

public interface MovieRoomService extends CommonService<MovieRoom> {
    boolean existsById(Long id);
}
