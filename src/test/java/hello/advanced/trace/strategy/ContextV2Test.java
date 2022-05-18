package hello.advanced.trace.strategy;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    @Test
    public void test1() {
        ContextV2 context2 = new ContextV2();
        context2.execute(new StrategyLogic1());

        ContextV2 context1 = new ContextV2();
        context1.execute(new StrategyLogic1());
    }
}
