package hello.advanced.pureproxy.cglib;

import hello.advanced.pureproxy.cglib.code.TimeInterceptor;
import hello.advanced.pureproxy.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTests {

    @Test
    void cglib() {
        ConcreteService target = new ConcreteService();

        Enhancer enhancer = new Enhancer();
        // 상속을 사용하기 떄문에, final 키워드에 대한 제약사항 및 기본생성자 필수에 대한 제약이 발생한다.
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeInterceptor(target));
        ConcreteService proxy = (ConcreteService)enhancer.create();

        log.info("targetClass={}", target.getClass());
        log.info("proxyClass={}", proxy.getClass());
        proxy.call();
    }
}
