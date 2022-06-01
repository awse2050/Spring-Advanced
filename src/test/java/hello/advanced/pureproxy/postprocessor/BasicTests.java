package hello.advanced.pureproxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
public class BasicTests {

    @Test
    public void basicBeanTest() {
        ApplicationContext context = new AnnotationConfigApplicationContext(BasicConfig.class);

        A beanA = (A) context.getBean("beanA");
        beanA.helloA();

        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> context.getBean(B.class));
    }

    @Configuration
    static class BasicConfig {
        @Bean(name = "beanA")
        public A a() {
            return new A();
        }

        @Bean
        public AToBPostProcessor postProcessor() {
            return new AToBPostProcessor();
        }
    }

    static class A {
        public void helloA() {
            log.info("helloA !!!");
        }
    }

    static class B {
         public void helloB() {
             log.info("helloB!!!");
         }
    }

    static class AToBPostProcessor implements BeanPostProcessor {
        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {}, bean = {}", beanName, bean);
            if( bean instanceof A) {
                return new B();
            }

            return bean;
        }
    }

}
