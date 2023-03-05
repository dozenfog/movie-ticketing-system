package by.issoft.service.impl;

import by.issoft.domain.user.City;
import by.issoft.repository.CityRepository;
import by.issoft.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CityServiceImpl implements CityService {
    private final CityRepository cityRepository;

    @Override
    public City save(City entity) {
        return cityRepository.save(entity);
    }

    @Override
    public List<City> findAll() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> findById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        cityRepository.deleteById(id);
    }
}
