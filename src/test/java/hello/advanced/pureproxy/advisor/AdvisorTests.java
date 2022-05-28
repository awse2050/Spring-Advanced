package hello.advanced.pureproxy.advisor;

import hello.advanced.pureproxy.common.advice.TimeAdvice;
import hello.advanced.pureproxy.concreteproxy.code.TimeProxy;
import hello.advanced.pureproxy.proxy.common.service.ServiceImpl;
import hello.advanced.pureproxy.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.DefaultAdvisorChainFactory;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTests {

    @Test
    @DisplayName("어드바이저 사용해보기.")
    public void advisorTest1() {
        ServiceInterface target = new ServiceImpl();
        // 프록시 팩토리를 생성한다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 프록시 팩토리는 Advisor의 호출을 통해서 Pointcut, advice를 호출하게 된다.

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        // proxyFactory.addAdvice(new TimeAdvice()) 와 같은 로직이다. 내부적으로 위와 같은 코드가 실행된다.
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }


    @Test
    @DisplayName("포인트컷 직접 사용해보기.")
    public void advisorTest2() {
        ServiceInterface target = new ServiceImpl();
        // 프록시 팩토리를 생성한다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 프록시 팩토리는 Advisor의 호출을 통해서 Pointcut, advice를 호출하게 된다.

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointcut(), new TimeAdvice());
        // proxyFactory.addAdvice(new TimeAdvice()) 와 같은 로직이다. 내부적으로 위와 같은 코드가 실행된다.
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

    static class MyPointcut implements Pointcut {
        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE; // 어떤 클래스를 필터링할지
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher {
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals("save");
            log.info("Method matcher result : {}, targetClass : {}", method.getName(), targetClass);
            return result;
        }

        // true인 경우 여러 인자까지 받는 matches 메서드를 통해서 값을 반환한다.
        @Override
        public boolean isRuntime() {
            return false;
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }

    @Test
    @DisplayName("스프링이 제공하는 포인트컷 사용하기")
    public void springPointcutTest() {
        ServiceInterface target = new ServiceImpl();
        // 프록시 팩토리를 생성한다.
        ProxyFactory proxyFactory = new ProxyFactory(target);
        // 프록시 팩토리는 Advisor의 호출을 통해서 Pointcut, advice를 호출하게 된다.
        // 기본적으로 스프링이 사용하는 포인트컷이다.
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("save");

        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        // proxyFactory.addAdvice(new TimeAdvice()) 와 같은 로직이다. 내부적으로 위와 같은 코드가 실행된다.
        proxyFactory.addAdvisor(advisor);

        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }

}
