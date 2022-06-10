package by.issoft.mock;

import lombok.extern.slf4j.Slf4j;
import org.mockito.MockSettings;
import org.mockito.Mockito;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static org.springframework.aop.support.AopUtils.getTargetClass;
import static org.springframework.test.util.ReflectionTestUtils.invokeMethod;

@Component
@Slf4j
@Order
public class MockCreatorBeanPostProcessor implements BeanPostProcessor {
    private static final List<String> PACKAGES = List.of(
            "by.issoft.repository",
            "by.issoft.dto.mapper",
            "by.issoft.service.impl",
            "by.issoft.externalapi.weather",
            "by.issoft.controller",
            "by.issoft.controller.admin"
    );

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        Class<?>[] interfaces = bean.getClass().getInterfaces();
        if (Arrays.stream(interfaces).anyMatch(i -> PACKAGES.contains(i.getPackageName()))
                || PACKAGES.contains(bean.getClass().getPackageName())) {
            return createRepositoryMock(bean, interfaces);
        }
        return bean;
    }

    public Object createRepositoryMock(Object bean, Class<?>[] interfaces) {
        MockSettings settings;
        if (interfaces.length == 0) {
            settings = Mockito
                    .withSettings()
                    .defaultAnswer(invocationOnMock -> invokeMethod(bean,
                            invocationOnMock.getMethod().getName(), invocationOnMock.getArguments()));
        } else {
            settings = Mockito
                    .withSettings()
                    .extraInterfaces(interfaces)
                    .defaultAnswer(invocationOnMock -> invokeMethod(bean,
                            invocationOnMock.getMethod().getName(), invocationOnMock.getArguments()));
        }
        Object mock = Mockito.mock(getTargetClass(bean), settings);
        MockResetTestExecutionListener.mocks.add(mock);
        return mock;
    }
}
