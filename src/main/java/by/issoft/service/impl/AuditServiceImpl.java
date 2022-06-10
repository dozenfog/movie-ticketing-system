package by.issoft.service.impl;

import by.issoft.domain.audit.Audit;
import by.issoft.repository.AuditRepository;
import by.issoft.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuditServiceImpl implements AuditService {
    private final AuditRepository auditRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Audit save(Audit entity) {
        return auditRepository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Audit> findAll() {
        return auditRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Audit> findById(Long id) {
        return auditRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        auditRepository.deleteById(id);
    }
}
