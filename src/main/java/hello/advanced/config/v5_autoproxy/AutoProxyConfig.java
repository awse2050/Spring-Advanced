package hello.advanced.config.v5_autoproxy;

import hello.advanced.config.AppV1Config;
import hello.advanced.config.AppV2Config;
import hello.advanced.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.advanced.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Import({AppV1Config.class, AppV2Config.class})
@Slf4j
@Configuration
public class AutoProxyConfig {

    // 스프링부트는 빈 후처리기를 사용하는 대신 자동 프록시 생성기를 이용한다.
    // 자동 프록시 생성기가 스프링 빈으로 등록되어있는 Advisor객체를 찾아서 자동으로 프록시를 적용한다.
    // 그래서 해당 Advisor가 가지고 있는 pointcut으로 어떤 프록시에 적용할 지 결정해서 프록시 객체를 만듬.
    // 만약 여러개가 있으면 여러개가 중복으로 적용된다.
//    @Bean
    public Advisor advisor1(LogTrace logTrace) {
        // 우리가 원하는 결과가 나오지 않는다.
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        // Advisor는 pointcut, advice가 필요하다.
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    // AspectJ 표현식을 사용해서 Advisor를 만든다.
//    @Bean
    public Advisor advisor2(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.advanced.proxy..*(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        //advisor = pointcut + advice
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.advanced.proxy..*(..)) && !execution(* hello.advanced.proxy..noLog(..))");
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        //advisor = pointcut + advice
        return new DefaultPointcutAdvisor(pointcut, advice);
    }
}
