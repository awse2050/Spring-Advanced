package hello.advanced.pureproxy.decorator;

import hello.advanced.pureproxy.decorator.code.*;
import org.junit.jupiter.api.Test;

public class DecoratorTests {

    @Test
    public void test1() {
        Component realComponent = new RealComponent();
        DecoratorClient client = new DecoratorClient(realComponent);
        client.execute();
    }

    @Test
    public void test2() {
        Component realComponent = new RealComponent();
        Component messageComponent = new MessageComponent(realComponent);
        DecoratorClient client = new DecoratorClient(messageComponent);
        client.execute();
    }

    @Test
    public void test3() {
        Component realComponent = new RealComponent();
        Component messageComponent = new MessageComponent(realComponent);
        Component timeComponent = new TimeComponent(messageComponent);
        DecoratorClient client = new DecoratorClient(timeComponent);
        client.execute();
    }
}
