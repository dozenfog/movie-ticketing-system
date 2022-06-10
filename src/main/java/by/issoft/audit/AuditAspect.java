package by.issoft.audit;

import by.issoft.domain.audit.Audit;
import by.issoft.service.AuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {
    private final AuditService auditService;

    @Pointcut("@annotation(Audit)")
    public void callWithAudit() {
    }

    @Around("callWithAudit()")
    public Object aroundCall(ProceedingJoinPoint pjp) throws Throwable {
        Audit audit = Audit.builder()
                .className(pjp.getTarget().getClass().getSimpleName())
                .methodName(pjp.getSignature().getName())
                .args(Arrays.stream(pjp.getArgs()).map(Object::toString).collect(Collectors.joining(", ")))
                .invocationTime(LocalDateTime.now())
                .build();

        try {
            Object returnedValue = pjp.proceed();
            audit.setReturnValue(String.valueOf(returnedValue));
            return returnedValue;
        } catch (Throwable e) {
            audit.setException(e.toString());
            throw e;
        } finally {
            try {
                audit.setExecutionTimeInNano(ChronoUnit.NANOS.between(audit.getInvocationTime(), LocalDateTime.now()));
                auditService.save(audit);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
    }
}
