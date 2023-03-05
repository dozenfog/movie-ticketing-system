package by.issoft.service.impl;

import by.issoft.domain.user.UserCategory;
import by.issoft.repository.UserCategoryRepository;
import by.issoft.service.UserCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserCategoryServiceImpl implements UserCategoryService {
    private final UserCategoryRepository userCategoryRepository;

    @Override
    public UserCategory save(UserCategory entity) {
        return userCategoryRepository.save(entity);
    }

    @Override
    public List<UserCategory> findAll() {
        return userCategoryRepository.findAll();
    }

    @Override
    public Optional<UserCategory> findById(Long id) {
        return userCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        userCategoryRepository.deleteById(id);
    }
}
