package hello.advanced.config.v1_proxy;

import hello.advanced.config.v1_proxy.concrete_proxy.OrderControllerConcreteProxy;
import hello.advanced.config.v1_proxy.concrete_proxy.OrderRepositoryConcreteProxy;
import hello.advanced.config.v1_proxy.concrete_proxy.OrderServiceConcreteProxy;
import hello.advanced.proxy.v2.OrderControllerV2;
import hello.advanced.proxy.v2.OrderRepositoryV2;
import hello.advanced.proxy.v2.OrderServiceV2;
import hello.advanced.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConcreteProxyConfig {

    @Bean
    public OrderRepositoryV2 orderRepositoryV2(LogTrace logTrace) {
        OrderRepositoryV2 realBean = new OrderRepositoryV2();
        return new OrderRepositoryConcreteProxy(realBean, logTrace);
    }

    @Bean
    public OrderServiceV2 orderServiceV2(LogTrace logTrace) {
        OrderServiceV2 realBean = new OrderServiceV2(orderRepositoryV2(logTrace));
        return new OrderServiceConcreteProxy(realBean, logTrace);
    }

    @Bean
    public OrderControllerV2 orderControllerV2(LogTrace logTrace) {
        OrderControllerV2 realBean = new OrderControllerV2(orderServiceV2(logTrace));
        return new OrderControllerConcreteProxy(realBean, logTrace);
    }

}
