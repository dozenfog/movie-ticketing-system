package by.issoft.mock;

import org.mockito.Mockito;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import java.util.ArrayList;
import java.util.List;

public class MockResetTestExecutionListener implements TestExecutionListener {
    public static final List<Object> mocks = new ArrayList<>();

    @Override
    public void afterTestMethod(TestContext testContext) {
        mocks.forEach(Mockito::reset);
    }
}
