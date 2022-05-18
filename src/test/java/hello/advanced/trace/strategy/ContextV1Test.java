package hello.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV1Test {

    @Test
    public void test1() {
        ContextV1 contextV1 = new ContextV1(new StrategyLogic1());
        contextV1.execute();
    }
}
