package hello.advanced.pureproxy.advisor;

import hello.advanced.pureproxy.proxy.common.service.ServiceImpl;
import hello.advanced.pureproxy.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;

@Slf4j
public class MultiAdvisorTests {

    @Test
    @DisplayName("여러개의 어드바이저 적용하기.")
    public void multiAdvisorTest() {
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory= new ProxyFactory(target);

        // 첫번째 포인트 컷을 적용
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        proxyFactory.addAdvisor(advisor1);
        ServiceInterface proxy1 = (ServiceInterface) proxyFactory.getProxy();

        ProxyFactory proxyFactory2 = new ProxyFactory(proxy1);
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());
        proxyFactory2.addAdvisor(advisor2);

        ServiceInterface proxy2 = (ServiceInterface) proxyFactory2.getProxy();

        proxy2.save();
        proxy2.find();
    }

    static class Advice1 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advice1 호출");
            return invocation.proceed();
        }
    }

    static class Advice2 implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            log.info("Advice2 호출");
            return invocation.proceed();
        }
    }


    @Test
    @DisplayName("여러개의 어드바이저 적용하기.")
    public void multiAdvisorTest2() {
        DefaultPointcutAdvisor advisor1 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice1());
        DefaultPointcutAdvisor advisor2 = new DefaultPointcutAdvisor(Pointcut.TRUE, new Advice2());

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory= new ProxyFactory(target);

        // 어드바이저를 여러개 적용한다.
        // 하나의 프록시가 여러개의 어드바이저를 호출하는 것이다.
        // 어드바이저 갯수만큼 프록시를 생성해서 호출하는 것이 아니다.
        proxyFactory.addAdvisor(advisor2);
        proxyFactory.addAdvisor(advisor1);

        ServiceInterface proxy =(ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();
    }
}
