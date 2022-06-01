package hello.advanced.config.v4_postprocessor.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

@Slf4j
public class PackageLogTraceProxyPostProcessor implements BeanPostProcessor {

    // 적용시킬 패키지 위치
    private final String basePackage;
    // 어드바이저
    private final Advisor advisor;

    public PackageLogTraceProxyPostProcessor(String basePackage, Advisor advisor) {
        this.basePackage = basePackage;
        this.advisor = advisor;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.info("beanName = {}, beanClass = {}", beanName, bean.getClass());
        // 프록시를 적용하지 않을 객체들은 그대로 반환한다.
        String packageName = bean.getClass().getPackageName();
        if(!packageName.startsWith(basePackage)) {
            return bean;
        }

        // 프록시를 적용해야 할 경우 어드바이저를 적용시켜서 프록시 객체를 반환한다.
        ProxyFactory proxyFactory = new ProxyFactory(bean);
        proxyFactory.addAdvisor(advisor);
        Object proxy = proxyFactory.getProxy();

        log.info("create proxy: target={} proxy={}", bean.getClass(),
                proxy.getClass());
        return proxy;
    }
}
