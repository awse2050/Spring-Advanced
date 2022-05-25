package hello.advanced.config.v2_dynamicproxy;

import hello.advanced.proxy.v1.*;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Proxy;

@Configuration
public class DynamicProxyFilterConfig {

    private static final String[] PATTERNS = {"req*", "order*", "save"};

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        // 실제 객체를 생성
        OrderControllerV1 orderController = new
                OrderControllerV1Impl(orderServiceV1(logTrace));
        // 실제로 사용할 객체를 프록시에 주입시키고 실제 실행할 로직으로 바꿔준다.
        OrderControllerV1 proxy = (OrderControllerV1)
                Proxy.newProxyInstance(OrderControllerV1.class.getClassLoader(),
                        new Class[]{OrderControllerV1.class},
                        new LogTraceFilterHandler(orderController, logTrace, PATTERNS)
                );
        return proxy;
    }
    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 orderService = new
                OrderServiceV1Impl(orderRepositoryV1(logTrace));
        OrderServiceV1 proxy = (OrderServiceV1)
                Proxy.newProxyInstance(OrderServiceV1.class.getClassLoader(),
                        new Class[]{OrderServiceV1.class},
                        new LogTraceFilterHandler(orderService, logTrace, PATTERNS)
                );
        return proxy;
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 orderRepository = new OrderRepositoryV1Impl();
        OrderRepositoryV1 proxy = (OrderRepositoryV1)
                Proxy.newProxyInstance(OrderRepositoryV1.class.getClassLoader(),
                        new Class[]{OrderRepositoryV1.class},
                        new LogTraceFilterHandler(orderRepository, logTrace, PATTERNS)
                );
        return proxy;
    }
}
