package by.issoft.service;

import by.issoft.domain.event.Event;

public interface EventService extends CommonService<Event> {
    boolean existsById(Long id);
}
