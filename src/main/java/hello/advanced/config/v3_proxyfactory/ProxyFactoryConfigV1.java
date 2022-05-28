package hello.advanced.config.v3_proxyfactory;

import hello.advanced.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.advanced.proxy.v1.*;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ProxyFactoryConfigV1 {

    @Bean
    public OrderRepositoryV1 orderRepositoryV1(LogTrace logTrace) {
        OrderRepositoryV1 target = new OrderRepositoryV1Impl();
        ProxyFactory factory = new ProxyFactory(target);

        factory.addAdvisor(getAdviser(logTrace));

        OrderRepositoryV1 proxy = (OrderRepositoryV1) factory.getProxy();
        log.info("OrderRepositoryV1 target = {} , proxy = {}", target.getClass(), proxy.getClass());
        return proxy;
    }

    @Bean
    public OrderServiceV1 orderServiceV1(LogTrace logTrace) {
        OrderServiceV1 target = new OrderServiceV1Impl(orderRepositoryV1(logTrace));
        ProxyFactory factory = new ProxyFactory(target);

        factory.addAdvisor(getAdviser(logTrace));

        OrderServiceV1 proxy = (OrderServiceV1) factory.getProxy();
        log.info("OrderServiceV1 target = {} , proxy = {}", target.getClass(), proxy.getClass());
        return proxy;
    }

    @Bean
    public OrderControllerV1 orderControllerV1(LogTrace logTrace) {
        OrderControllerV1 target = new OrderControllerV1Impl(orderServiceV1(logTrace));
        ProxyFactory factory = new ProxyFactory(target);

        factory.addAdvisor(getAdviser(logTrace));

        OrderControllerV1 proxy = (OrderControllerV1) factory.getProxy();
        log.info("OrderControllerV1 target = {} , proxy = {}", target.getClass(), proxy.getClass());
        return proxy;
    }


    private Advisor getAdviser(LogTrace logTrace) {
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        LogTraceAdvice logTraceAdvice = new LogTraceAdvice(logTrace);

        return new DefaultPointcutAdvisor(pointcut, logTraceAdvice);
    }
}
