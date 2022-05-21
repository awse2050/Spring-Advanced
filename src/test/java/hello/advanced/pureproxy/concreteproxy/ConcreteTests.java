package hello.advanced.pureproxy.concreteproxy;

import hello.advanced.pureproxy.concreteproxy.code.ConcreteClient;
import hello.advanced.pureproxy.concreteproxy.code.ConcreteLogic;
import hello.advanced.pureproxy.concreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteTests {

    @Test
    public void test1() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient concreteClient = new ConcreteClient(concreteLogic);
        concreteClient.execute();
    }

    @Test
    public void test2() {
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient concreteClient = new ConcreteClient(timeProxy);
        concreteClient.execute();
    }

}
