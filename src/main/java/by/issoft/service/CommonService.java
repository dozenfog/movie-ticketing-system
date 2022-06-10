package by.issoft.service;

import by.issoft.domain.AbstractEntity;

import java.util.List;
import java.util.Optional;

public interface CommonService<E extends AbstractEntity> {
    E save(E entity);

    List<E> findAll();

    Optional<E> findById(Long id);

    void delete(Long id);
}
